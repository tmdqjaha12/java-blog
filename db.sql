# 캐릭터SET 설정
SET NAMES utf8mb4;

# DB 생성
DROP DATABASE IF EXISTS site36;
CREATE DATABASE site36;
USE site36;

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

# 1번글 생성
INSERT INTO article SET
regDate = NOW(),
updateDate = NOW(),
cateItemId = 6,
displayStatus = 1,
title = '블로그를 시작합니다.',
`body` = ''








DELETE 
 FROM article 
 WHERE 1 
 AND id = 353;
 
 DELETE FROM article WHERE 1 AND id = 1;
 
DROP TABLE IF EXISTS `member`;
CREATE TABLE `member` (
    id INT(10) UNSIGNED NOT NULL PRIMARY KEY AUTO_INCREMENT,
    regDate DATETIME NOT NULL,
    updateDate DATETIME NOT NULL,
    memberStatus TINYINT(1) UNSIGNED NOT NULL,
    `loginId` CHAR(200) NOT NULL,
    `name` CHAR(200) NOT NULL,
    `nickname` CHAR(200) NOT NULL,
    `loginPw` CHAR(200) NOT NULL,
    `loginPwConfirm` CHAR(200) NOT NULL
);

INSERT INTO `member` 
SET regDate = NOW(),
updateDate = NOW(),
memberStatus = 0,
`loginId` = 'meloporn',
`name` = '승범',
`nickname` = '메로폰',
`loginPw` = 'sbs123414',
`loginPwConfirm` = 'sbs123414';

SELECT *
FROM `member`;