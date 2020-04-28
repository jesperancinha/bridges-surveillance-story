delete
from company;

drop sequence if exists hibernate_sequence;

create sequence hibernate_sequence;

alter sequence hibernate_sequence owner to postgres;
