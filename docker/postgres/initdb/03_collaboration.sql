create table dispatcher_last_event
(
    event_id bigint
        constraint dispatcher_last_event_pk primary key
);

create table calendars
(
    calendar_id         char(36)
        constraint calendars_pk
            primary key,
    description         varchar(500),
    name                varchar(100) not null,
    owner_email_address varchar(100) not null,
    owner_identity      varchar(50)  not null,
    owner_name          varchar(200) not null,
    tenant_id           char(36)     not null
        constraint fk_calendars_tenants_tenant_id_id
            references tenants
);

create index idx_calendars_owner_identity on calendars (owner_identity);

create index idx_calendars_tenant_id on calendars (tenant_id);

create table calendar_entries
(
    calendar_entry_id      char(36)     not null
        constraint calendar_entry_pk
            primary key,
    alarm_alarm_units      integer      not null,
    alarm_alarm_units_type varchar(10)  not null,
    calendar_id            char(36)     not null
        constraint fk_calendar_entries_calendars_calendar_id
            references calendars,
    description            varchar(500),
    location               varchar(100),
    owner_email_address    varchar(100) not null,
    owner_identity         varchar(50)  not null,
    owner_name             varchar(200) not null,
    repetition_ends        timestamp    not null,
    repetition_type        varchar(20)  not null,
    tenant_id              char(36)     not null
        constraint fk_calendar_entries_tenants_tenant_id_id
            references tenants,
    time_span_begins       timestamp    not null,
    time_span_ends         timestamp    not null
);

create index idx_calendar_entries_calendar_id on calendar_entries (calendar_id);

create index idx_calendar_entries_owner_identity on calendar_entries (owner_identity);

create index idx_calendar_entries_repetition_ends on calendar_entries (repetition_ends);

create index idx_calendar_entries_tenant_id on calendar_entries (tenant_id);

create index idx_calendar_entries_time_span_begins on calendar_entries (time_span_begins);

create index idx_calendar_entries_time_span_ends on calendar_entries (time_span_ends);


create table calendar_entry_invitees
(
    id                        serial
        constraint calendar_entry_invitees_pk
            primary key,
    calendar_entry_id         char(36)     not null
        constraint fk_calendar_entry_invitees_calendar_entries_calendar_entry_id
            references calendar_entries,
    participant_email_address varchar(100) not null,
    participant_identity      varchar(50)  not null,
    participant_name          varchar(200) not null,
    tenant_id                 char(36)     not null
        constraint fk_calendar_entry_invitees_tenants_tenant_id_id
            references tenants
);

create index idx_calendar_entry_invitees_calendar_entry_id on calendar_entry_invitees (calendar_entry_id);

create index idx_calendar_entry_invitees_participant_identity on calendar_entry_invitees (participant_identity);

create index idx_calendar_entry_invitees_tenant_id on calendar_entry_invitees (tenant_id);


create table calendar_entry_sharers
(
    id                        serial
        constraint calendar_entry_sharers_pk
            primary key,
    calendar_id               char(36)     not null
        constraint fk_calendar_entry_shares_calendars_calendar_id
            references calendars,
    participant_email_address varchar(100) not null,
    participant_identity      varchar(50)  not null,
    participant_name          varchar(200) not null,
    tenant_id                 char(36)     not null
        constraint fk_calendar_entry_sharers_tenants_tenant_id_id
            references tenants
);

create index idx_calendar_entry_sharers_calendar_id on calendar_entry_sharers (calendar_id);

create index idx_calendar_entry_sharers_participant_identity on calendar_entry_sharers (participant_identity);

create index idx_calendar_entry_sharers_tenant_id on calendar_entry_sharers (tenant_id);


create table forums
(
    forum_id                char(36)
        constraint forums_pk
            primary key,
    closed                  boolean      not null,
    creator_email_address   varchar(100) not null,
    creator_identity        varchar(50)  not null,
    creator_name            varchar(200) not null,
    description             varchar(500) not null,
    exclusive_owner         varchar(100),
    moderator_email_address varchar(100) not null,
    moderator_identity      varchar(50)  not null,
    moderator_name          varchar(200) not null,
    subject                 varchar(100) not null,
    tenant_id               char(36)     not null
        constraint fk_forums_tenants_tenant_id_id
            references tenants
);

create index idx_forums_creator_identity on forums (creator_identity);

create index idx_forums_exclusive_owner on forums (exclusive_owner);

create index idx_forums_tenant_id on forums (tenant_id);


create table discussions
(
    discussion_id        char(36)
        constraint discussions_pk
            primary key,
    author_email_address varchar(100) not null,
    author_identity      varchar(50)  not null,
    author_name          varchar(200) not null,
    closed               boolean      not null,
    exclusive_owner      varchar(100),
    forum_id             char(36)     not null
        constraint fk_discussions_forums_forum_id
            references forums,
    subject              varchar(100) not null,
    tenant_id            char(36)     not null
        constraint fk_discussions_tenants_tenant_id_id
            references tenants
);

create index idx_discussions_author_identity on discussions (author_identity);

create index idx_discussions_forum_id on discussions (forum_id);

create index idx_discussions_tenant_id on discussions (tenant_id);

create index idx_discussions_exclusive_owner on discussions (exclusive_owner);


create table posts
(
    post_id              char(36)     not null
        constraint posts_pk
            primary key,
    author_email_address varchar(100) not null,
    author_identity      varchar(50)  not null,
    author_name          varchar(200) not null,
    body_text            text         not null,
    changed_on           timestamp    not null,
    created_on           timestamp    not null,
    discussion_id        char(36)     not null
        constraint fk_posts_discussions_discussion_id
            references discussions,
    forum_id             char(36)     not null
        constraint fk_posts_forums_forum_id
            references forums,
    reply_to_post_id     char(36)     not null,
    subject              varchar(100) not null,
    tenant_id            char(36)     not null
);

create index idx_posts_author_identity on posts (author_identity);

create index idx_posts_discussion_id on posts (discussion_id);

create index idx_posts_forum_id on posts (forum_id);

create index idx_posts_reply_to_post_id on posts (reply_to_post_id);

create index idx_posts_tenant_id on posts (tenant_id);