(function () {
    const state = {
        stomp: null,
        roomId: null,
        buildingId: null,
        staffId: null,
        typingTimer: null,
        typingSent: false,
        roomSubscription: null,
        typingSubscription: null
    };
    const viewerType = "CUSTOMER";

    function escapeHtml(value) {
        return String(value || "")
            .replaceAll("&", "&amp;")
            .replaceAll("<", "&lt;")
            .replaceAll(">", "&gt;")
            .replaceAll('"', "&quot;")
            .replaceAll("'", "&#39;");
    }

    function attrEncode(value) {
        return JSON.stringify(value || "").replace(/"/g, "&quot;");
    }

    function ensureConnected() {
        return new Promise((resolve) => {
            if (state.stomp && state.stomp.connected) {
                resolve();
                return;
            }

            const socket = new SockJS("/ws-chat");
            const client = Stomp.over(socket);
            client.debug = null;
            client.connect({}, () => {
                state.stomp = client;
                resolve();
            }, () => resolve());
        });
    }

    function scrollBottom() {
        const box = document.getElementById("customerChatMessages");
        if (box) box.scrollTop = box.scrollHeight;
    }

    function renderMessages(messages) {
        const box = $("#customerChatMessages");
        box.empty();
        if (!messages || messages.length === 0) {
            $("#customerChatEmpty").removeClass("d-none");
            return;
        }
        $("#customerChatEmpty").addClass("d-none");
        messages.forEach(appendMessage);
        scrollBottom();
    }

    function appendMessage(message) {
        const mine = String(message.senderType || "").toUpperCase() === viewerType;
        $("#customerChatMessages").append(`
            <div class="d-flex ${mine ? "justify-content-end" : "justify-content-start"} mb-2">
                <div class="px-3 py-2 rounded-3 ${mine ? "text-white" : "bg-light"}"
                     style="max-width:78%;${mine ? "background:#3b6ef0;" : ""}">
                    <div style="font-size:11px;opacity:.75" class="mb-1">${escapeHtml(message.senderName || "")}</div>
                    <div style="white-space:pre-wrap;word-break:break-word">${escapeHtml(message.content || "")}</div>
                    <div style="font-size:10px;opacity:.7" class="mt-1">${message.createdAt ? new Date(message.createdAt).toLocaleString("vi-VN") : ""}</div>
                </div>
            </div>`);
        scrollBottom();
    }

    function showTyping(isTyping) {
        const el = $("#customerChatTyping");
        if (!el.length) return;
        el.toggleClass("d-none", !isTyping);
    }

    function subscribeRoom(roomId) {
        if (!state.stomp || !state.stomp.connected) return;
        if (state.roomSubscription) state.roomSubscription.unsubscribe();
        if (state.typingSubscription) state.typingSubscription.unsubscribe();

        state.roomSubscription = state.stomp.subscribe(`/topic/chat/room/${roomId}`, function (frame) {
            const payload = JSON.parse(frame.body);
            appendMessage(payload);
            $("#customerChatEmpty").addClass("d-none");
        });

        state.typingSubscription = state.stomp.subscribe(`/topic/chat/typing/${roomId}`, function (frame) {
            const payload = JSON.parse(frame.body);
            const typing = payload && payload.typing;
            const mine = payload && payload.senderType === "CUSTOMER";
            if (!mine) {
                showTyping(typing);
            }
        });
    }

    async function openRoom(buildingId, staffId, buildingName, staffName) {
        try {
            const response = await $.ajax({
                url: "/customer/chat/rooms/open",
                method: "POST",
                contentType: "application/json",
                data: JSON.stringify({
                    buildingId: Number(buildingId),
                    staffId: Number(staffId)
                })
        });

        state.buildingId = Number(buildingId);
        state.staffId = Number(staffId);
        state.roomId = response.room.roomId;

            $("#customerChatTitle").text(buildingName || response.room.buildingName || "Hỗ trợ");
            $("#customerChatSubtitle").text(`${staffName || response.room.staffName || ""}${response.room.staffPhone ? " | " + response.room.staffPhone : ""}`);

            $("#customerChatInput").val("");
            $("#customerChatTyping").addClass("d-none");
            $("#staffSelectModal").modal("hide");
            $("#customerChatModal").modal("show");
            renderMessages(response.messages || []);

            await ensureConnected();
            subscribeRoom(state.roomId);
        } catch (error) {
            const message = error?.responseJSON?.message || "Không thể mở cuộc hội thoại.";
            if (window.Swal) {
                Swal.fire({ icon: "error", title: "Không thể liên hệ", text: message });
            } else {
                alert(message);
            }
        }
    }

    async function openSelector(buildingId, buildingName) {
        try {
            const staffs = await $.get(`/customer/chat/buildings/${buildingId}/staffs`);
            const list = $("#staffSelectList");
            list.empty();
            $("#staffSelectTitle").text(buildingName || "Chọn nhân viên");

            if (!staffs || staffs.length === 0) {
                list.append(`<div class="text-muted small">Tòa nhà này chưa có nhân viên quản lý.</div>`);
            } else {
                staffs.forEach(staff => {
                    const avatar = staff.image ? `/images/staff_img/${staff.image}` :
                        'data:image/svg+xml,%3Csvg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 100 100"%3E%3Crect width="100" height="100" fill="%23dbe4ff"/%3E%3Ccircle cx="50" cy="38" r="18" fill="%235b89ff"/%3E%3Ccircle cx="50" cy="90" r="30" fill="%235b89ff"/%3E%3C/svg%3E';
                    list.append(`
                        <button type="button" class="list-group-item list-group-item-action d-flex align-items-center gap-3"
                                onclick="ChatCustomer.openRoom(${buildingId}, ${staff.staffId}, ${attrEncode(buildingName || "")}, ${attrEncode(staff.fullName || "")})">
                            <img src="${avatar}" alt="" style="width:42px;height:42px;border-radius:50%;object-fit:cover;">
                            <div class="flex-grow-1 text-start">
                                <div class="fw-semibold">${escapeHtml(staff.fullName || "")}</div>
                                <div class="small text-muted">${escapeHtml(staff.phone || "")}</div>
                            </div>
                            <i class="bi bi-chat-dots text-primary"></i>
                        </button>
                    `);
                });
            }

            $(".modal.show").modal("hide");
            await new Promise(resolve => setTimeout(resolve, 180));
            $("#staffSelectModal").modal("show");
        } catch (error) {
            const message = error?.responseJSON?.message || "Không thể tải danh sách nhân viên.";
            if (window.Swal) {
                Swal.fire({ icon: "error", title: "Liên hệ thất bại", text: message });
            } else {
                alert(message);
            }
        }
    }

    async function sendMessage() {
        if (!state.roomId) return;
        const input = $("#customerChatInput");
        const content = input.val().trim();
        if (!content) return;

        input.prop("disabled", true);
        $("#customerChatSendBtn").prop("disabled", true);
        try {
            await $.ajax({
                url: `/customer/chat/rooms/${state.roomId}/messages`,
                method: "POST",
                contentType: "application/json",
                data: JSON.stringify({ content })
            });
            input.val("");
            showTyping(false);
        } catch (error) {
            const message = error?.responseJSON?.message || "Không thể gửi tin nhắn.";
            if (window.Swal) {
                Swal.fire({ icon: "error", title: "Gửi thất bại", text: message });
            } else {
                alert(message);
            }
        } finally {
            input.prop("disabled", false);
            $("#customerChatSendBtn").prop("disabled", false);
            input.trigger("focus");
        }
    }

    async function closeRoom() {
        if (!state.roomId) return;
        try {
            await $.post(`/customer/chat/rooms/${state.roomId}/close`);
            $("#customerChatModal").modal("hide");
            state.roomId = null;
            showTyping(false);
        } catch (error) {
            const message = error?.responseJSON?.message || "Không thể đóng hội thoại.";
            if (window.Swal) {
                Swal.fire({ icon: "error", title: "Đóng hội thoại thất bại", text: message });
            } else {
                alert(message);
            }
        }
    }

    function bindEvents() {
        $("#customerChatSendBtn").on("click", sendMessage);
        $("#customerChatCloseRoomBtn").on("click", closeRoom);

        $("#customerChatInput").on("input", function () {
            if (!state.roomId || !state.stomp || !state.stomp.connected) return;
            clearTimeout(state.typingTimer);

            if (!state.typingSent) {
                state.stomp.send("/app/chat/typing", {}, JSON.stringify({ roomId: state.roomId, typing: true }));
                state.typingSent = true;
            }

            state.typingTimer = setTimeout(() => {
                if (state.stomp && state.stomp.connected) {
                    state.stomp.send("/app/chat/typing", {}, JSON.stringify({ roomId: state.roomId, typing: false }));
                }
                state.typingSent = false;
            }, 900);
        });

        $("#customerChatInput").on("keydown", function (e) {
            if (e.key === "Enter" && !e.shiftKey) {
                e.preventDefault();
                sendMessage();
            }
        });

        $("#customerChatModal").on("hidden.bs.modal", function () {
            showTyping(false);
            if (state.roomSubscription) {
                state.roomSubscription.unsubscribe();
                state.roomSubscription = null;
            }
            if (state.typingSubscription) {
                state.typingSubscription.unsubscribe();
                state.typingSubscription = null;
            }
            state.roomId = null;
        });
    }

    window.ChatCustomer = {
        openSelector,
        openRoom,
        sendMessage,
        closeRoom,
        loadMessages: renderMessages
    };

    $(bindEvents);
})();
