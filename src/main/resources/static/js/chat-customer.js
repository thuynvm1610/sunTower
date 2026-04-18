(function () {
    const state = {
        stomp: null,
        roomId: null,
        roomSummary: null,
        buildingId: null,
        staffId: null,
        typingTimer: null,
        typingSent: false,
        roomSubscription: null,
        typingSubscription: null,
        notificationSubscription: null
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

    function getRoomLabel(room) {
        if (!room) return "";
        const parts = [];
        if (room.staffName) parts.push(room.staffName);
        if (room.staffPhone) parts.push(room.staffPhone);
        return parts.join(" | ");
    }

    function ensureCustomerChatDom() {
        if (!document.getElementById("customerChatNotificationBtn")) {
            const actionHost =
                document.getElementById("customerChatHeaderHost")
                || document.querySelector(".header-top .d-flex.justify-content-between > .d-flex.align-items-center.gap-3")
                || document.querySelector(".header-top .d-flex.justify-content-between > .d-flex.align-items-center")
                || document.querySelector(".header-top .d-flex.justify-content-between");
            const logoutBtn = document.querySelector(".btn-logout");
            if (actionHost) {
                const wrapper = document.createElement("div");
                wrapper.id = "customerChatNotificationWrap";
                wrapper.className = "dropdown";
                wrapper.innerHTML = `
                    <button class="btn btn-outline-primary btn-sm position-relative" id="customerChatNotificationBtn" data-bs-toggle="dropdown" aria-expanded="false" type="button" title="Hội thoại hỗ trợ">
                        <i class="bi bi-bell"></i>
                        <span class="position-absolute top-0 start-100 translate-middle badge rounded-pill bg-danger d-none" id="customerChatUnreadBadge"></span>
                    </button>
                    <ul class="dropdown-menu dropdown-menu-end p-0 shadow" id="customerChatDropdownMenu" style="min-width:340px; max-height:380px; overflow-y:auto;"></ul>
                `;
                if (logoutBtn && logoutBtn.parentElement === actionHost) {
                    actionHost.insertBefore(wrapper, logoutBtn);
                } else {
                    actionHost.appendChild(wrapper);
                }
            }
        }

        if (!document.getElementById("staffSelectModal")) {
            const selectModal = document.createElement("div");
            selectModal.id = "customerChatSelectModalHost";
            selectModal.innerHTML = `
                <div class="modal fade" id="staffSelectModal" tabindex="-1" aria-hidden="true">
                    <div class="modal-dialog modal-dialog-centered modal-dialog-scrollable">
                        <div class="modal-content">
                            <div class="modal-header">
                                <div>
                                    <h5 class="modal-title" id="staffSelectTitle">Chọn nhân viên</h5>
                                    <div class="small opacity-75">Chọn 1 nhân viên để mở cuộc hội thoại.</div>
                                </div>
                                <button type="button" class="btn-close" data-bs-dismiss="modal"></button>
                            </div>
                            <div class="modal-body">
                                <div class="list-group" id="staffSelectList"></div>
                            </div>
                        </div>
                    </div>
                </div>
            `;
            document.body.appendChild(selectModal);
        }

        if (!document.getElementById("customerChatModal")) {
            const chatModal = document.createElement("div");
            chatModal.id = "customerChatModalHost";
            chatModal.innerHTML = `
                <div class="modal fade" id="customerChatModal" tabindex="-1" aria-hidden="true">
                    <div class="modal-dialog modal-dialog-centered modal-lg modal-dialog-scrollable">
                        <div class="modal-content">
                            <div class="modal-header">
                                <div>
                                    <h5 class="modal-title" id="customerChatTitle">Hỗ trợ</h5>
                                    <div class="small opacity-75" id="customerChatSubtitle"></div>
                                </div>
                                <button type="button" class="btn-close" data-bs-dismiss="modal"></button>
                            </div>
                            <div class="modal-body">
                                <div id="customerChatMessages" style="min-height:320px;max-height:420px;overflow-y:auto;"></div>
                                <div id="customerChatEmpty" class="text-center text-muted py-5">Chưa có tin nhắn nào</div>
                                <div id="customerChatTyping" class="small text-primary d-none mb-2">... Đang soạn tin</div>
                            </div>
                            <div class="modal-footer d-flex flex-column align-items-stretch gap-2">
                                <textarea id="customerChatInput" class="form-control" rows="2" placeholder="Nhập tin nhắn..."></textarea>
                                <div class="d-flex justify-content-end gap-2">
                                    <button type="button" class="btn btn-outline-secondary" id="customerChatCloseRoomBtn">Đóng hội thoại</button>
                                    <button type="button" class="btn btn-primary" id="customerChatSendBtn">Gửi</button>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            `;
            document.body.appendChild(chatModal);
        }
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
            if (state.roomId === Number(roomId) && String(payload.senderType || "").toUpperCase() !== viewerType) {
                markCurrentRoomRead().then(loadInbox);
            } else {
                loadInbox();
            }
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

    async function openRoom(buildingId, staffId, buildingName, staffName, silent = false) {
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
            state.roomSummary = response.room;

            $("#customerChatTitle").text(buildingName || response.room.buildingName || "Hỗ trợ");
            $("#customerChatSubtitle").text(`${staffName || response.room.staffName || ""}${response.room.staffPhone ? " | " + response.room.staffPhone : ""}`);

            $("#customerChatInput").val("");
            $("#customerChatTyping").addClass("d-none");
            $("#staffSelectModal").modal("hide");
            $("#customerChatModal").modal("show");
            renderMessages(response.messages || []);

            await ensureConnected();
            subscribeNotifications();
            subscribeRoom(state.roomId);
            markCurrentRoomRead().catch(console.error);
            loadInbox().catch(console.error);
            return response;
        } catch (error) {
            if (silent) {
                console.error(error);
                return null;
            }
            const message = error?.responseJSON?.message || "Không thể mở cuộc hội thoại.";
            if (window.Swal) {
                Swal.fire({ icon: "error", title: "Không thể liên hệ", text: message });
            } else {
                alert(message);
            }
            throw error;
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
                    const avatar = (staff.image || '').trim()
                        ? staff.image
                        : 'data:image/svg+xml,%3Csvg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 100 100"%3E%3Crect width="100" height="100" fill="%23dbe4ff"/%3E%3Ccircle cx="50" cy="38" r="18" fill="%235b89ff"/%3E%3Ccircle cx="50" cy="90" r="30" fill="%235b89ff"/%3E%3C/svg%3E';
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

    async function markCurrentRoomRead() {
        if (!state.roomId) return;
        try {
            await $.post(`/customer/chat/rooms/${state.roomId}/read`);
        } catch (error) {
            console.error(error);
        }
    }

    async function loadInbox() {
        try {
            const rooms = await $.get("/customer/chat/rooms");
            const menu = $("#customerChatDropdownMenu");
            const badge = $("#customerChatUnreadBadge");
            if (!menu.length) return;

            menu.empty();
            let unreadTotal = 0;

            if (!rooms || rooms.length === 0) {
                menu.append(`<li><span class="dropdown-item-text text-muted small">Chưa có cuộc hội thoại nào</span></li>`);
            } else {
                rooms.forEach(room => {
                    unreadTotal += Number(room.unreadCount || 0);
                    const preview = room.lastMessage || "Cuộc hội thoại mới";
                    const title = `${room.staffName || ""}${room.staffPhone ? " | " + room.staffPhone : ""}`.trim();
                    menu.append(`
                        <li>
                    <button class="dropdown-item customer-chat-room-item text-start" type="button"
                                    data-building-id="${room.buildingId}"
                                    data-room-id="${room.roomId}"
                                    data-staff-id="${room.staffId}"
                                    data-building-name="${escapeHtml(room.buildingName || "")}"
                                    data-staff-name="${escapeHtml(room.staffName || "")}"
                                    data-staff-phone="${escapeHtml(room.staffPhone || "")}">
                                <div class="d-flex justify-content-between gap-3">
                                    <div class="text-start">
                                        <div class="fw-semibold">${escapeHtml(title || "")}</div>
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

    function subscribeNotifications() {
        if (!state.stomp || !state.stomp.connected || state.notificationSubscription) return;
        state.notificationSubscription = state.stomp.subscribe("/user/queue/chat/notifications", function () {
            loadInbox();
        });
    }

    async function openExistingRoom(roomId, buildingId, staffId, buildingName, staffName, staffPhone, silent = false) {
        try {
            const response = await $.post(`/customer/chat/rooms/${roomId}/resume`);
            state.roomId = response.room.roomId;
            state.roomSummary = response.room;

            $("#customerChatTitle").text(buildingName || response.room.buildingName || "Hỗ trợ");
            $("#customerChatSubtitle").text(`${staffName || response.room.staffName || ""}${staffPhone || response.room.staffPhone ? " | " + (staffPhone || response.room.staffPhone || "") : ""}`);
            $("#customerChatInput").val("");
            $("#customerChatTyping").addClass("d-none");
            $("#customerChatModal").modal("show");
            renderMessages(response.messages || []);

            await ensureConnected();
            subscribeNotifications();
            subscribeRoom(state.roomId);
            markCurrentRoomRead().catch(console.error);
            loadInbox().catch(console.error);
            return response;
        } catch (error) {
            if (buildingId && staffId) {
                try {
                    return await openRoom(buildingId, staffId, buildingName, staffName, true);
                } catch (fallbackError) {
                    console.error(fallbackError);
                    return;
                }
            }
            if (silent) {
                console.error(error);
                return null;
            }
            const message = error?.responseJSON?.message || "Không thể mở cuộc hội thoại.";
            if (window.Swal) {
                Swal.fire({ icon: "error", title: "Không thể mở hội thoại", text: message });
            } else {
                alert(message);
            }
            throw error;
        }
    }

    async function openConversationFromInbox(roomId, buildingId, staffId, buildingName, staffName, staffPhone) {
        const normalizedBuildingId = Number(buildingId);
        const normalizedStaffId = Number(staffId);

        if (normalizedBuildingId && normalizedStaffId) {
            const opened = await openRoom(normalizedBuildingId, normalizedStaffId, buildingName, staffName, true);
            if (opened) {
                return opened;
            }
        }

        if (roomId) {
            const resumed = await openExistingRoom(roomId, normalizedBuildingId, normalizedStaffId, buildingName, staffName, staffPhone, true);
            if (resumed) {
                return resumed;
            }
        }

        return null;
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
        ensureCustomerChatDom();
        $("#customerChatSendBtn").on("click", sendMessage);
        $("#customerChatCloseRoomBtn").on("click", closeRoom);
        $("#customerChatNotificationBtn").on("click", function () {
            loadInbox();
        });

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

        $(document).on("click", ".customer-chat-room-item", function () {
            const buildingId = Number($(this).data("building-id"));
            const staffId = Number($(this).data("staff-id"));
            const roomId = Number($(this).data("room-id"));
            const buildingName = String($(this).data("building-name") || "");
            const staffName = String($(this).data("staff-name") || "");
            const staffPhone = String($(this).data("staff-phone") || "");
            openConversationFromInbox(roomId, buildingId, staffId, buildingName, staffName, staffPhone)
                .catch(console.error);
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
    $(function () {
        ensureCustomerChatDom();
        ensureConnected().then(subscribeNotifications);
        loadInbox();
    });
})();
