create table tenants
(
    id                  char(36)
        constraint tenants_pk
            primary key,
    active              boolean      not null,
    description         varchar(100) not null,
    name                varchar(100) not null unique,
    concurrency_version integer      not null
);


create table users
(
    id                  char(36)
        constraint users_pk
            primary key,
    enablement_enabled  boolean      not null,
    enablement_end_date timestamp    not null,
    password            varchar(100) not null,
    tenant_id           char(36)     not null
        constraint fk_users_tenants_tenant_id_id
            references tenants,
    username            varchar(250) not null,
    concurrency_version integer      not null,
    constraint uq_users_tenant_id_user_name
        unique (tenant_id, username)
);

create index idx_users_tenant_id on users (tenant_id);

create table groups
(
    id                  char(36)
        constraint groups_pk
            primary key,
    description         varchar(250) not null,
    name                varchar(100) not null,
    tenant_id           char(36)     not null
        constraint fk_groups_tenants_tenant_id_id
            references tenants,
    concurrency_version integer      not null,
    constraint uq_groups_tenant_id_name
        unique (name, tenant_id)
);

create index groups_tenant_id_index
    on groups (tenant_id);

create table group_members
(
    id        char(36)
        constraint group_member_pk
            primary key,
    name      varchar(100) not null,
    tenant_id char(36)     not null
        constraint fk_group_members_tenants_tenant_id_id
            references tenants,
    type      varchar(5)   not null,
    group_id  char(36)     not null
        constraint fk_group_members_groups_group_id_id
            references groups
);

create index group_member_group_id_index
    on group_members (group_id);

create index group_member_tenant_id_index
    on group_members (tenant_id);

create table persons
(
    id                  char(36)
        constraint person_pk
            primary key,
    email               varchar(100) not null,
    address             varchar(255) not null,
    postal_code         integer      not null,
    telephone_number    varchar(20)  not null,
    first_name          varchar(50)  not null,
    last_name           varchar(50)  not null,
    tenant_id           char(36)     not null
        constraint fk_persons_tenants_tenant_id_id
            references tenants,
    concurrency_version integer      not null,
    constraint uq_persons_tenant_id_email
        unique (tenant_id, email)
);

comment on column persons.email is 'メールアドレス';

comment on column persons.address is '住所';

comment on column persons.postal_code is '郵便番号';

comment on column persons.telephone_number is '電話番号';

comment on column persons.first_name is '名';

comment on column persons.last_name is '姓';

create index idx_persons_tenant_id
    on persons (tenant_id);


create table roles
(
    id                  char(36)
        constraint roles_pk
            primary key,
    description         varchar(250) not null,
    group_id            char(36)     not null
        constraint fk_roles_groups_group_id_id
            references groups,
    name                varchar(100) not null,
    supports_nesting    boolean      not null,
    tenant_id           char(36)     not null
        constraint fk_roles_tenants_tenant_id_id
            references tenants,
    concurrency_version int          not null,
    constraint uq_roles_tenant_id_name
        unique (tenant_id, name)
);

create index idx_roles_tenant_id on roles (tenant_id);

create table registration_invitations
(
    id          char(36)
        constraint registration_invitations_pk
            primary key,
    description varchar(100) not null,
    starting_on timestamp    not null,
    tenant_id   char(36)     not null
        constraint fk_registration_invitations_tenants_tenant_id_id
            references tenants,
    until timestamp,
    concurrency_version integer not null
);

create index idx_registration_invitations_tenant_id on registration_invitations (tenant_id);