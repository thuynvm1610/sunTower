(function () {
    const state = {
        stomp: null,
        roomId: null,
        typingTimer: null,
        typingSent: false,
        roomSubscription: null,
        typingSubscription: null,
        notificationSubscription: null
    };
    const viewerType = "STAFF";

    function escapeHtml(value) {
        return String(value || "")
            .replaceAll("&", "&amp;")
            .replaceAll("<", "&lt;")
            .replaceAll(">", "&gt;")
            .replaceAll('"', "&quot;")
            .replaceAll("'", "&#39;");
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
        const box = document.getElementById("staffChatMessages");
        if (box) box.scrollTop = box.scrollHeight;
    }

    function renderMessages(messages) {
        const box = $("#staffChatMessages");
        box.empty();
        if (!messages || messages.length === 0) {
            $("#staffChatEmpty").removeClass("d-none");
            return;
        }
        $("#staffChatEmpty").addClass("d-none");
        messages.forEach(appendMessage);
        scrollBottom();
    }

    function appendMessage(message) {
        const mine = String(message.senderType || "").toUpperCase() === viewerType;
        $("#staffChatMessages").append(`
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

    async function markCurrentRoomRead() {
        if (!state.roomId) return;
        try {
            await $.post(`/staff/chat/rooms/${state.roomId}/read`);
        } catch (error) {
            console.error(error);
        }
    }

    function showTyping(isTyping) {
        const el = $("#staffChatTyping");
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
            $("#staffChatEmpty").addClass("d-none");
            if (state.roomId === Number(roomId) && String(payload.senderType || "").toUpperCase() !== viewerType) {
                markCurrentRoomRead().then(loadInbox);
            } else {
                loadInbox();
            }
        });

        state.typingSubscription = state.stomp.subscribe(`/topic/chat/typing/${roomId}`, function (frame) {
            const payload = JSON.parse(frame.body);
            const typing = payload && payload.typing;
            const mine = payload && payload.senderType === "STAFF";
            if (!mine) {
                showTyping(typing);
            }
        });
    }

    function subscribeNotifications() {
        if (!state.stomp || !state.stomp.connected || state.notificationSubscription) return;
        state.notificationSubscription = state.stomp.subscribe("/user/queue/chat/notifications", function () {
            loadInbox();
        });
    }

    async function loadInbox() {
        try {
            const rooms = await $.get("/staff/chat/rooms");
            const menu = $("#chatDropdownMenu");
            const badge = $("#chatUnreadBadge");
            if (!menu.length) return;

            menu.empty();
            let unreadTotal = 0;

            if (!rooms || rooms.length === 0) {
                menu.append(`<li><span class="dropdown-item-text text-muted small">Chưa có hội thoại nào</span></li>`);
            } else {
                rooms.forEach(room => {
                    unreadTotal += Number(room.unreadCount || 0);
                    const preview = room.lastMessage || "Cuộc hội thoại mới";
                    menu.append(`
                        <li>
                            <button class="dropdown-item chat-room-item" type="button"
                                    data-room-id="${room.roomId}"
                                    data-customer-name="${escapeHtml(room.customerName || "")}"
                                    data-customer-phone="${escapeHtml(room.customerPhone || "")}">
                                <div class="d-flex justify-content-between gap-3">
                                    <div class="text-start">
                                        <div class="fw-semibold">${escapeHtml((room.customerName || "") + (room.customerPhone ? " | " + room.customerPhone : ""))}</div>
                                        <div class="small text-muted">${escapeHtml(room.buildingName || "")}</div>
                                        <div class="small text-truncate" style="max-width:250px;">${escapeHtml(preview)}</div>
                                    </div>
                                    <div class="text-end small">
                                        ${room.unreadCount ? `<span class="badge bg-primary rounded-pill">${room.unreadCount}</span>` : ""}
                                    </div>
                                </div>
                            </button>
                        </li>
                    `);
                });
            }

            if (badge.length) {
                badge.text(unreadTotal > 0 ? unreadTotal : "");
                badge.toggleClass("d-none", unreadTotal <= 0);
            }
        } catch (error) {
            console.error(error);
        }
    }

    async function openRoom(roomId, customerName, customerPhone) {
        try {
            const messages = await $.get(`/staff/chat/rooms/${roomId}/messages`);
            state.roomId = roomId;
            $("#staffChatTitle").text("Hội thoại hỗ trợ");
            $("#staffChatSubtitle").text(`${customerName || "Khách hàng"}${customerPhone ? " | " + customerPhone : ""}`);
            $("#staffChatInput").val("");
            $("#staffChatTyping").addClass("d-none");
            $("#staffChatModal").modal("show");
            renderMessages(messages || []);
            await ensureConnected();
            subscribeRoom(roomId);
            await markCurrentRoomRead();
            await loadInbox();
        } catch (error) {
            const message = error?.responseJSON?.message || "Không thể mở hội thoại.";
            if (window.Swal) {
                Swal.fire({ icon: "error", title: "Lỗi hội thoại", text: message });
            } else {
                alert(message);
            }
        }
    }

    async function sendMessage() {
        if (!state.roomId) return;
        const input = $("#staffChatInput");
        const content = input.val().trim();
        if (!content) return;

        input.prop("disabled", true);
        $("#staffChatSendBtn").prop("disabled", true);
        try {
            await $.ajax({
                url: `/staff/chat/rooms/${state.roomId}/messages`,
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
            $("#staffChatSendBtn").prop("disabled", false);
            input.trigger("focus");
        }
    }

    async function closeRoom() {
        if (!state.roomId) return;
        try {
            await $.post(`/staff/chat/rooms/${state.roomId}/close`);
            $("#staffChatModal").modal("hide");
            state.roomId = null;
            showTyping(false);
            loadInbox();
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
        $("#staffChatSendBtn").on("click", sendMessage);
        $("#staffChatCloseRoomBtn").on("click", closeRoom);

        $("#staffChatInput").on("input", function () {
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

        $("#staffChatInput").on("keydown", function (e) {
            if (e.key === "Enter" && !e.shiftKey) {
                e.preventDefault();
                sendMessage();
            }
        });

        $("#staffChatModal").on("hidden.bs.modal", function () {
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

        $("#chatNotificationBtn").on("click", function () {
            loadInbox();
        });

        $(document).on("click", ".chat-room-item", function () {
            const roomId = Number($(this).data("room-id"));
            const customerName = String($(this).data("customer-name") || "");
            const customerPhone = String($(this).data("customer-phone") || "");
            if (roomId) {
                ChatStaff.openRoom(roomId, customerName, customerPhone);
            }
        });
    }

    window.ChatStaff = {
        openRoom,
        sendMessage,
        closeRoom,
        loadInbox
    };

    $(function () {
        bindEvents();
        ensureConnected().then(subscribeNotifications);
        loadInbox();
    });
})();
