DROP TABLE IF EXISTS player;

CREATE TABLE player
(
    id                  INT AUTO_INCREMENT PRIMARY KEY,
    name                VARCHAR(250) NOT NULL,
    MONTH_OF_Experience INT          NOT NULL
);

