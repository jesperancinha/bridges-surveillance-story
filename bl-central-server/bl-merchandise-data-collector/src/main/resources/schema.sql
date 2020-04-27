drop table if exists merchandise;

create table merchandise
(
    id                      varchar(255) not null
        constraint merchandise_pkey
            primary key,
    destination_location_id bigint,
    product_id              bigint,
    quantity                bigint,
    source_location_id      bigint,
    supplier_id             bigint,
    timetamp                bigint,
    volume_per_unit         bigint
);

alter table merchandise
    owner to postgres;

