CREATE TABLE `ms_object`
(
    `id`          bigint unsigned NOT NULL AUTO_INCREMENT,
    `name`        varchar(1024)   NOT NULL DEFAULT '',
    `gmt_created` datetime        NOT NULL DEFAULT CURRENT_TIMESTAMP,
    `gmt_updated` datetime        NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    `is_deleted`  bit(1)          NOT NULL DEFAULT 0,
    PRIMARY KEY (`id`),
    UNIQUE INDEX `object_idx_uq` (`name`)
);

CREATE TABLE `ms_tag`
(
    `id`          bigint unsigned NOT NULL AUTO_INCREMENT,
    `name`        varchar(255)    NOT NULL DEFAULT '',
    `gmt_created` datetime        NOT NULL DEFAULT CURRENT_TIMESTAMP,
    `gmt_updated` datetime        NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    `is_deleted`  bit(1)          NOT NULL DEFAULT 0,
    PRIMARY KEY (`id`),
    UNIQUE INDEX `tag_idx_uq` (`name`)
);

CREATE TABLE `ms_object_tag`
(
    `id`          bigint unsigned NOT NULL AUTO_INCREMENT,
    `object_id`   bigint          NOT NULL,
    `tag_id`      bigint          NOT NULL,
    `gmt_created` datetime        NOT NULL DEFAULT CURRENT_TIMESTAMP,
    `gmt_updated` datetime        NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    `is_deleted`  bit(1)          NOT NULL DEFAULT 0,
    PRIMARY KEY (`id`),
    UNIQUE INDEX `object_tag_idx_uq` (`object_id`, `tag_id`),
    INDEX `object_tag_idx_tag` (`tag_id`)
);