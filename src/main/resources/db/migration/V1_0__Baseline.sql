CREATE TABLE IF NOT EXISTS `post`
(
    `id`           uuid         NOT NULL,
    `title`        VARCHAR(255) NOT NULL,
    `route`        VARCHAR(255) NOT NULL,
    `date_posted`  bigint       NOT NULL,
    `last_updated` bigint       NOT NULL,
    `file_name`    VARCHAR(255) NOT NULL,
    `category`     VARCHAR(255) NOT NULL,
    PRIMARY KEY (`id`)
);
