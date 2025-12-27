# generate_invoice_data_full_to_prev_month_option_B.py
# Chức năng:
#  - Với mỗi contract: sinh utility_meter, invoice, invoice_detail
#  - Từ contract.start_date → tháng trước của tháng hiện tại (NOW - 1)
#  - Tháng trước (gần nhất) -> PENDING, paid_date = NULL
#  - Các tháng trước nữa -> PAID, paid_date random

import mysql.connector
from datetime import datetime, timedelta
import random

# ============================================
# 1. CONNECT DATABASE (chỉnh lại nếu cần)
# ============================================
db = mysql.connector.connect(
    host="localhost",
    user="root",
    password="123456",
    database="estate"
)
cursor = db.cursor(dictionary=True)

# ============================================
# 2. LOAD DATA
# ============================================
cursor.execute("SELECT * FROM contract")
contracts = cursor.fetchall()

cursor.execute("SELECT * FROM building")
buildings = {b["id"]: b for b in cursor.fetchall()}

# ============================================
# 3. HELPERS
# ============================================
def month_year_iter(start_date, end_date):
    cur = start_date.replace(day=1)
    end = end_date.replace(day=1)
    while cur <= end:
        yield cur.month, cur.year
        if cur.month == 12:
            cur = cur.replace(month=1, year=cur.year + 1)
        else:
            cur = cur.replace(month=cur.month + 1)

def get_invoice_created_date(month, year):
    if month == 12:
        return datetime(year + 1, 1, 1, 0, 0, 0)
    else:
        return datetime(year, month + 1, 1, 0, 0, 0)

def random_datetime_between(start_dt, end_dt):
    delta = int((end_dt - start_dt).total_seconds())
    if delta <= 5:
        return start_dt + timedelta(seconds=3)
    return start_dt + timedelta(seconds=random.randint(3, delta - 1))

# ============================================
# 4. XÁC ĐỊNH THÁNG TRƯỚC
# ============================================
NOW = datetime.now()

if NOW.month == 1:
    LAST_MONTH = 12
    LAST_YEAR = NOW.year - 1
else:
    LAST_MONTH = NOW.month - 1
    LAST_YEAR = NOW.year

# ============================================
# 5. GENERATE DATA – OPTION B
# ============================================
for c in contracts:

    if c["building_id"] not in buildings:
        print(f"Skip contract {c['id']}: no building")
        continue

    building = buildings[c["building_id"]]

    contract_start = c["start_date"]
    contract_end = c["end_date"]

    # chỉ generate tới tháng trước
    last_month_date = datetime(LAST_YEAR, LAST_MONTH, 1)

    start_month_first = contract_start.replace(day=1)
    end_month_first = min(contract_end.replace(day=1), last_month_date)

    if start_month_first > end_month_first:
        print(f"Contract {c['id']} has no months to generate.")
        continue

    print(f"Contract {c['id']}... generating from {start_month_first} → {end_month_first}")

    last_elec = 0
    last_water = 0

    for m, y in month_year_iter(start_month_first, end_month_first):

        created_date = get_invoice_created_date(m, y)
        due_date = created_date + timedelta(days=15)

        # ==============================
        # STATUS RULE (Option B)
        # ==============================
        if m == LAST_MONTH and y == LAST_YEAR:
            status = "PENDING"
            paid_date = None
        else:
            status = "PAID"
            paid_date = random_datetime_between(created_date, due_date)

        # Utility
        elec_new = last_elec + random.randint(80, 250)
        water_new = last_water + random.randint(10, 40)

        cursor.execute("""
            INSERT INTO utility_meter (
                contract_id, month, year,
                electricity_old, electricity_new,
                water_old, water_new,
                created_at
            ) VALUES (%s,%s,%s,%s,%s,%s,%s,%s)
        """, (
            c["id"], m, y,
            last_elec, elec_new,
            last_water, water_new,
            created_date
        ))

        elec_used = elec_new - last_elec
        water_used = water_new - last_water

        last_elec = elec_new
        last_water = water_new

        # ==============================
        # FEES (đơn giá — dùng cho invoice_detail, giữ nguyên)
        # ==============================
        rent_fee = float(c["rent_price"])
        service_fee = float(building["service_fee"])
        car_fee = float(building["car_fee"])
        motorbike_fee = float(building["motorbike_fee"])
        electricity_fee = elec_used * float(building["electricity_fee"])
        water_fee = water_used * float(building["water_fee"])

        line_items = [
            ("Tiền thuê mặt bằng", rent_fee),
            ("Phí dịch vụ", service_fee),
            ("Phí gửi ô tô", car_fee),
            ("Phí gửi xe máy", motorbike_fee),
            ("Phí điện", electricity_fee),
            ("Phí nước", water_fee),
        ]

        # ==============================
        # TOTAL AMOUNT (CÔNG THỨC YÊU CẦU)
        # ==============================
        rent_total = float(c["rent_price"]) * int(c["rent_area"])
        service_total = float(building["service_fee"])
        car_total = float(building["car_fee"])
        motorbike_total = float(building["motorbike_fee"])
        electricity_total = elec_used * float(building["electricity_fee"])
        water_total = water_used * float(building["water_fee"])

        total_amount = (
            rent_total
            + service_total
            + car_total
            + motorbike_total
            + electricity_total
            + water_total
        )

        cursor.execute("""
            INSERT INTO invoice (
                contract_id, customer_id, month, year,
                total_amount, status,
                created_date, due_date, paid_date
            ) VALUES (%s,%s,%s,%s,%s,%s,%s,%s,%s)
        """, (
            c["id"], c["customer_id"],
            m, y,
            total_amount, status,
            created_date, due_date, paid_date
        ))

        invoice_id = cursor.lastrowid

        for desc, amount in line_items:
            cursor.execute("""
                INSERT INTO invoice_detail (invoice_id, description, amount)
                VALUES (%s,%s,%s)
            """, (invoice_id, desc, amount))

    db.commit()

cursor.close()
db.close()

print("=== DONE: Older months = PAID, Last month = PENDING ===")
