-- begin CEEM_ORDER
alter table CEEM_ORDER add constraint FK_CEEM_ORDER_CUSTOMER foreign key (CUSTOMER_ID) references CEEM_CUSTOMER(ID)^
create index IDX_CEEM_ORDER_CUSTOMER on CEEM_ORDER (CUSTOMER_ID)^
-- end CEEM_ORDER
