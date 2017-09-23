-- begin CEEM_CUSTOMER
create table CEEM_CUSTOMER (
    ID varchar(36) not null,
    VERSION integer not null,
    CREATE_TS timestamp,
    CREATED_BY varchar(50),
    UPDATE_TS timestamp,
    UPDATED_BY varchar(50),
    DELETE_TS timestamp,
    DELETED_BY varchar(50),
    --
    NAME varchar(255),
    --
    primary key (ID)
)^
-- end CEEM_CUSTOMER
-- begin CEEM_ORDER
create table CEEM_ORDER (
    ID varchar(36) not null,
    VERSION integer not null,
    CREATE_TS timestamp,
    CREATED_BY varchar(50),
    UPDATE_TS timestamp,
    UPDATED_BY varchar(50),
    DELETE_TS timestamp,
    DELETED_BY varchar(50),
    --
    ORDER_DATE date not null,
    AMOUNT integer not null,
    CUSTOMER_ID varchar(36) not null,
    --
    primary key (ID)
)^
-- end CEEM_ORDER
-- begin CEEM_PRESENT
create table CEEM_PRESENT (
    ID varchar(36) not null,
    VERSION integer not null,
    CREATE_TS timestamp,
    CREATED_BY varchar(50),
    UPDATE_TS timestamp,
    UPDATED_BY varchar(50),
    DELETE_TS timestamp,
    DELETED_BY varchar(50),
    --
    CUSTOMER_NAME varchar(255) not null,
    AMOUNT integer not null,
    --
    primary key (ID)
)^
-- end CEEM_PRESENT
