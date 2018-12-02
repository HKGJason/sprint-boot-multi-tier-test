alter table if exists parking_lot add column if not exists parking_lot_id varchar not null;

ALTER TABLE PARKING_BOY MODIFY COLUMN ID BIGINT(19) auto_increment;

ALTER TABLE PARKING_LOT MODIFY COLUMN ID BIGINT(19) auto_increment;

ALTER TABLE if EXISTS parking_lot
    add column if NOT EXISTS parking_lot_id VARCHAR(64) DEFAULT '' NOT NULL;

ALTER TABLE if EXISTS parking_lot
ADD CONSTRAINT UQ__PARKING_LOT_ID UNIQUE(parking_lot_id);

ALTER TABLE if EXISTS parking_lot
add column if NOT EXISTS available_position_count int default 0 not null;