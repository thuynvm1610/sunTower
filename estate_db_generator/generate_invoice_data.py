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

NOW = datetime.now()


# ============================================
# 3. HELPERS
# ============================================

def month_year_iter(start_date, end_date):
    """Loop qua từng tháng giữa start_date và end_date."""
    cur = start_date.replace(day=1)
    end = end_date.replace(day=1)
    while cur <= end:
        yield cur.month, cur.year
        # tăng sang tháng tiếp theo
        if cur.month == 12:
            cur = cur.replace(month=1, year=cur.year + 1)
        else:
            cur = cur.replace(month=cur.month + 1)


def get_invoice_created_date(month, year):
    """Trả về ngày 1 tháng kế tiếp lúc 00:00:00"""
    if month == 12:
        return datetime(year + 1, 1, 1, 0, 0, 0)
    else:
        return datetime(year, month + 1, 1, 0, 0, 0)


def random_datetime_between(start_dt, end_dt):
    """Random datetime giữa start_dt và end_dt"""
    delta_seconds = int((end_dt - start_dt).total_seconds())
    if delta_seconds <= 2:
        return start_dt + timedelta(seconds=1)
    rnd = random.randint(1, delta_seconds - 1)
    return start_dt + timedelta(seconds=rnd)


# ============================================
# 4. GENERATE DATA
# ============================================
for c in contracts:
    building = buildings[c["building_id"]]

    last_elec = 0
    last_water = 0

    print(f"Processing contract {c['id']} ...")

    for month, year in month_year_iter(c["start_date"], c["end_date"]):

        # === A. Tính ngày tạo hóa đơn + ngày hết hạn ===
        created_date = get_invoice_created_date(month, year)
        due_date = created_date + timedelta(days=15)

        # === B. Xác định trạng thái ===
        invoice_month_start = datetime(year, month, 1)
        first_of_current_month = datetime(NOW.year, NOW.month, 1)

        if invoice_month_start < first_of_current_month:
            status = "PAID"
        else:
            status = "PENDING"

        # === C. Sinh chỉ số điện nước ===
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
            c["id"], month, year,
            last_elec, elec_new,
            last_water, water_new,
            created_date  # Giống created_date của invoice
        ))

        elec_used = elec_new - last_elec
        water_used = water_new - last_water

        last_elec = elec_new
        last_water = water_new

        # === D. Tính các khoản phí ===
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
            ("Tiền điện", electricity_fee),
            ("Tiền nước", water_fee),
        ]

        total_amount = sum(a for _, a in line_items)

        # === E. Tạo invoice ===
        cursor.execute("""
            INSERT INTO invoice (
                contract_id, customer_id, month, year,
                total_amount, status,
                created_date, due_date, paid_date
            ) VALUES (%s,%s,%s,%s,%s,%s,%s,%s,%s)
        """, (
            c["id"], c["customer_id"],
            month, year,
            total_amount, status,
            created_date, due_date,
            None  # cập nhật sau nếu PAID
        ))

        invoice_id = cursor.lastrowid

        # === F. Nếu PAID → sinh paid_date ===
        if status == "PAID":
            paid_date = random_datetime_between(created_date, due_date)
            cursor.execute(
                "UPDATE invoice SET paid_date=%s WHERE id=%s",
                (paid_date, invoice_id)
            )

        # === G. Insert invoice_detail ===
        for desc, amount in line_items:
            cursor.execute("""
                INSERT INTO invoice_detail (invoice_id, description, amount)
                VALUES (%s,%s,%s)
            """, (invoice_id, desc, amount))

db.commit()
cursor.close()
db.close()

print("=== DONE: Generated invoices, invoice_detail, utility_meter ===")
