# 캐릭터SET 설정
SET NAMES utf8mb4;

# DB 생성
DROP DATABASE IF EXISTS site41;
CREATE DATABASE site41;
USE site41;

# 카테고리 테이블 생성
DROP TABLE IF EXISTS cateItem;
CREATE TABLE cateItem (
    id INT(10) UNSIGNED NOT NULL PRIMARY KEY AUTO_INCREMENT,
    regDate DATETIME NOT NULL,
    `name` CHAR(100) NOT NULL UNIQUE
);

# 카테고리 추가
INSERT INTO cateItem SET regDate = NOW(), `name` = 'IT/일반';
INSERT INTO cateItem SET regDate = NOW(), `name` = 'IT/알고리즘';
INSERT INTO cateItem SET regDate = NOW(), `name` = 'IT/프론트엔드';
INSERT INTO cateItem SET regDate = NOW(), `name` = 'IT/백엔드';
INSERT INTO cateItem SET regDate = NOW(), `name` = '디자인/피그마';
INSERT INTO cateItem SET regDate = NOW(), `name` = '일상/일반';

# 게시물 테이블 생성
DROP TABLE IF EXISTS article;
CREATE TABLE article (
    id INT(10) UNSIGNED NOT NULL PRIMARY KEY AUTO_INCREMENT,
    regDate DATETIME NOT NULL,
    updateDate DATETIME NOT NULL,
    cateItemId INT(10) UNSIGNED NOT NULL,
    displayStatus TINYINT(1) UNSIGNED NOT NULL,
    `title` CHAR(200) NOT NULL,
    `body` TEXT NOT NULL
);
SELECT *
FROM article

# 댓글 테이블 생성
DROP TABLE IF EXISTS articleReply;
CREATE TABLE articleReply(
    id INT(10) UNSIGNED NOT NULL PRIMARY KEY AUTO_INCREMENT,
    regDate DATETIME NOT NULL,
    updateDate DATETIME NOT NULL,
    articleId INT(10) UNSIGNED NOT NULL,
    memberId INT(10) UNSIGNED NOT NULL,
    `body` TEXT NOT NULL
);
SELECT *
FROM articleReply;

DELETE FROM articleReply WHERE 1 AND id = 10;

UPDATE articleReply
SET regDate = '2020-07-22 11:32:51'
, updateDate = NOW()
, articleId =  132
, memberId = 28
, 
28
132
16
2020-07-22 11:32:51
ㅇㅁㅇㅁ

INSERT INTO articleReply
SET regDate = NOW()
,updateDate = NOW()
,articleId = 3
,memberId = 3
,`body` = '111';

# 1번글 생성
INSERT INTO article SET
regDate = NOW(),
updateDate = NOW(),
cateItemId = 6,
memberId = 2,
displayStatus = 1,
title = '블로그를 시작합니다.',
`body` = '111',
hit = 0;

# 회원 테이블 생성
DROP TABLE IF EXISTS `member`;
CREATE TABLE `member` (
    id INT(10) UNSIGNED NOT NULL PRIMARY KEY AUTO_INCREMENT,
    regDate DATETIME NOT NULL,
    updateDate DATETIME NOT NULL,
    `loginId` CHAR(100) NOT NULL UNIQUE,
    `loginPw` CHAR(100) NOT NULL,
    `name` CHAR(100) NOT NULL, 
    `nickname` CHAR(100) NOT NULL UNIQUE,
    `email` CHAR(100) NOT NULL,
    `level` INT(1) UNSIGNED DEFAULT 0 NOT NULL,
    `mailAuthCode` INT(1) UNSIGNED DEFAULT 0 NOT NULL
);

ALTER TABLE `member` ADD COLUMN mailAuthCode INT(10) UNSIGNED NOT NULL AFTER `level`;

SELECT *
FROM `member`;

SELECT mailAuthCode
FROM `member`
WHERE id = 19

SELECT COUNT(*) AS cnt
FROM `member`
WHERE `name` = 12
AND `email` = '12@12';

SELECT loginId
FROM `member`
WHERE `name` = 12
AND `email` = '12@12';

select *
from `member`;

update `member`
set nickname = 'hihihi'
where `name` = '12';

SELECT nickname
FROM `member`
WHERE id = 1

# 마스터 회원 생성
INSERT INTO `member` SET
regDate = NOW(),
updateDate = NOW(),
`loginId` = 'admin',
`loginPw` = 'admin',
`name` = 'admin',
`nickname` = 'admin',
`email` = 'admin@admin.com',
`level` = 10;

# 게시물에 memberId 칼럼 추가
ALTER TABLE article ADD COLUMN memberId INT(10) UNSIGNED NOT NULL AFTER cateItemId;
# 게시물에 hit 칼럼 추가
ALTER TABLE article ADD COLUMN hit INT(10) UNSIGNED NOT NULL AFTER `body`;

# 기존 게시물의 작성자 번호를 1번으로 정리(통일)
UPDATE article
SET memberId = 1
WHERE memberId = 0;

# 리스트 불러오기
SELECT *
FROM article
WHERE displayStatus = 1
ORDER BY id DESC
LIMIT 0, 10