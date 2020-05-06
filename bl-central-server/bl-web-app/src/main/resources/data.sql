delete from train_container;

delete from trains;

delete from freight_container;

delete from freight;

delete from company;

drop sequence if exists hibernate_sequence;

create sequence hibernate_sequence;

alter sequence hibernate_sequence owner to postgres;
