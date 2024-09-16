INSERT INTO asset (id, customer_id, asset_name, size, usable_size)
VALUES
    (1, 1, 'TRY', 10000.00, 8000.00);  -- 10,000 total, 8,000 usable

-- TRY asset for normal user (id: 2)
INSERT INTO asset (id, customer_id, asset_name, size, usable_size)
VALUES
    (2, 2, 'TRY', 5000.00, 5000.00);  -- 5,000 total, 5,000 usable

-- TRY asset for another normal user (id: 3)
INSERT INTO asset (id, customer_id, asset_name, size, usable_size)
VALUES
    (3, 3, 'TRY', 3000.00, 2500.00);
