create table event_store
(
    event_id       bigserial
        constraint event_store_pk
            primary key,
    event_body     json         not null,
    event_type     varchar(250) not null,
    stream_name    varchar(250) not null,
    stream_version integer      not null
        constraint stream_version_should_be_positive
            check (stream_version > 0),
    constraint uq_stream_name_stream_version
        unique (stream_name, stream_version)
);

comment on table event_store is 'イベントストア（イベントソーシング用）';


create table sorted_event
(
    event_id    bigserial
        constraint sorted_event_pk
            primary key,
    event_body  json         not null,
    occurred_on timestamp    not null,
    type_name   varchar(200) not null
);

create table published_notification_tracker
(
    published_notification_tracker_id     bigserial
        constraint published_notification_tracker_pk
            primary key,
    most_recent_published_notification_id bigint       not null,
    type_name                             varchar(100) not null,
    concurrency_version                   integer      not null
);



create table time_constrained_process_tracker
(
    time_constrained_process_tracker_id bigserial    not null
        constraint time_constrained_process_tracker_pk
            primary key,
    allowed_duration                    bigint       not null,
    completed                           boolean      not null,
    description                         varchar(100) not null,
    process_id                          varchar(36)  not null,
    process_informed_of_timeout         boolean      not null,
    process_timed_out_event_type        varchar(200) not null,
    retry_count                         integer      not null,
    tenant_id                           char(36)     not null
        constraint fk_time_constraint_process_tracker_tenants_tenant_id_id
            references tenants,
    timeout_occurs_on                   bigint       not null,
    total_retries_permitted             bigint       not null,
    concurrency_version                 integer      not null
);

create index time_constrained_process_tracker_process_id_index
    on time_constrained_process_tracker (process_id);

create index time_constrained_process_tracker_tenant_id_index
    on time_constrained_process_tracker (tenant_id);

create index time_constrained_process_tracker_timeout_occurs_on_index
    on time_constrained_process_tracker (timeout_occurs_on);