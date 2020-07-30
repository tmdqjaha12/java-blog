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

# 댓글 테이블 추가
DROP TABLE IF EXISTS articleReply;
CREATE TABLE articleReply (
    id INT(10) UNSIGNED NOT NULL PRIMARY KEY AUTO_INCREMENT,
    regDate DATETIME NOT NULL,
    updateDate DATETIME NOT NULL,
    memberId INT(10) UNSIGNED NOT NULL,
    displayStatus TINYINT(1) UNSIGNED NOT NULL,
    `body` TEXT NOT NULL
);

ALTER TABLE articleReply ADD COLUMN articleId INT(10) UNSIGNED NOT NULL DEFAULT 0 AFTER memberId;

# 기존 게시물 댓글의 게시물 번호를 1번으로 정리(통일)
UPDATE articleReply
SET articleId = 1
WHERE articleId = 0; 

###########3

# 캐릭터SET 설정
SET NAMES utf8mb4;

# DB 생성
DROP DATABASE IF EXISTS site36; # site36 있으면 삭제
CREATE DATABASE site36; # site36 DB생성
USE site36; # site36 DB 선택

# 카테고리 테이블 생성
DROP TABLE IF EXISTS cateItem; # 카테고리 테이블 있으면 삭제
CREATE TABLE cateItem ( # 카테고리 테이블 생성
    id INT(10) UNSIGNED NOT NULL PRIMARY KEY AUTO_INCREMENT, # 카테고리 테이블 ID
    regDate DATETIME NOT NULL, # 날짜
    `name` CHAR(100) NOT NULL UNIQUE # 카테고리 이름
);

# 카테고리 추가
INSERT INTO cateItem SET regDate = NOW(), `name` = 'IT/일반'; # IT/일반 카테고리 추가
INSERT INTO cateItem SET regDate = NOW(), `name` = 'IT/알고리즘';
INSERT INTO cateItem SET regDate = NOW(), `name` = 'IT/프론트엔드';
INSERT INTO cateItem SET regDate = NOW(), `name` = 'IT/백엔드';
INSERT INTO cateItem SET regDate = NOW(), `name` = '디자인/피그마';
INSERT INTO cateItem SET regDate = NOW(), `name` = '일상/일반';

# 게시물 테이블 생성
DROP TABLE IF EXISTS article; # 게시물 테이블 있으면 삭제
CREATE TABLE article ( # 게시물 테이블 생성
    id INT(10) UNSIGNED NOT NULL PRIMARY KEY AUTO_INCREMENT, # 게시물 테이블 ID
    regDate DATETIME NOT NULL, # 게시물 날짜
    updateDate DATETIME NOT NULL, # 업데이트 날짜
    cateItemId INT(10) UNSIGNED NOT NULL, # 카테고리 ID
    displayStatus TINYINT(1) UNSIGNED NOT NULL, # 게시물 열람 권한
    `title` CHAR(200) NOT NULL, # 게시물 제목
    `body` TEXT NOT NULL # 게시물 내용
);

# 1번글 생성
INSERT INTO article SET
regDate = NOW(),
updateDate = NOW(),
cateItemId = 6,
displayStatus = 1,
title = '블로그를 시작합니다.',
`body` = '';

# 회원 테이블 생성
DROP TABLE IF EXISTS `member`; # 회원 테이블 있으면 삭제
CREATE TABLE `member` ( # 회원 테이블 생성
    id INT(10) UNSIGNED NOT NULL PRIMARY KEY AUTO_INCREMENT, # 회원 테이블 ID
    regDate DATETIME NOT NULL, # 가입 날짜
    updateDate DATETIME NOT NULL, # 회원 정보 수정 날짜
    `loginId` CHAR(100) NOT NULL UNIQUE, # 회원 로그인 아이디
    `loginPw` CHAR(100) NOT NULL, # 회원 로그인 비번
    `name` CHAR(100) NOT NULL,  # 회원 이름
    `nickname` CHAR(100) NOT NULL UNIQUE, # 회원 별면
    `email` CHAR(100) NOT NULL, # 회원 이메일
    `level` INT(1) UNSIGNED DEFAULT 0 NOT NULL # 회원 권한 레벨
);

# 마스터 회원 생성
INSERT INTO `member` SET # 멤버 테이블에 ADMIN생성
regDate = NOW(), # 생성날짜
updateDate = NOW(), # 정보수정 날짜
`loginId` = 'admin', # 아이디
`loginPw` = SHA2('admin', 256), # 비밀번호
`name` = 'admin', # 이름
`nickname` = 'admin', # 닉네임
`email` = 'admin@admin.com', # 이메일
`level` = 10; # 권한레벨 10

# 게시물에 memberId 칼럼 추가
ALTER TABLE article ADD COLUMN hit INT(10) UNSIGNED NOT NULL DEFAULT 0 AFTER cateItemId;

# 게시물에 memberId 칼럼 추가
ALTER TABLE article ADD COLUMN memberId INT(10) UNSIGNED NOT NULL AFTER cateItemId;

# 댓글 테이블 생성
DROP TABLE IF EXISTS articleReply;  # 댓글 테이블 있으면 삭제
CREATE TABLE articleReply ( # 댓글 테이블 생성
    id INT(10) UNSIGNED NOT NULL PRIMARY KEY AUTO_INCREMENT, # 댓글 테이블 ID
    regDate DATETIME NOT NULL, # 댓글 생성 날짜
    updateDate DATETIME NOT NULL, # 댓글 수정 날짜
    memberId INT(10) UNSIGNED NOT NULL, # 댓글 멤버 ID
    displayStatus TINYINT(1) UNSIGNED NOT NULL, # 댓글 열람 권한
    `body` TEXT NOT NULL # 댓글 내용
);

# 댓글 테이블에 mberId 칼럼 추가
ALTER TABLE articleReply ADD COLUMN articleId INT(10) UNSIGNED NOT NULL DEFAULT 0 AFTER memberId;


# 부가정보테이블 
# 댓글 테이블 추가
DROP TABLE IF EXISTS attr;
CREATE TABLE attr (
    id INT(10) UNSIGNED NOT NULL PRIMARY KEY AUTO_INCREMENT,
    regDate DATETIME NOT NULL,
    updateDate DATETIME NOT NULL,
    `name` CHAR(100) NOT NULL UNIQUE,
    `value` TEXT NOT NULL
);

# updateDate 칼럼 추가
ALTER TABLE `cateItem` ADD COLUMN `updateDate` DATETIME NOT NULL AFTER `regDate`; 

# attr 테이블에서 name 을 4가지 칼럼으로 나누기
ALTER TABLE `attr` DROP COLUMN `name`,
ADD COLUMN `relTypeCode` CHAR(20) NOT NULL AFTER `updateDate`,
ADD COLUMN `relId` INT(10) UNSIGNED NOT NULL AFTER `relTypeCode`,
ADD COLUMN `typeCode` CHAR(30) NOT NULL AFTER `relId`,
ADD COLUMN `type2Code` CHAR(30) NOT NULL AFTER `typeCode`,
CHANGE `value` `value` TEXT CHARSET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL AFTER `type2Code`,
DROP INDEX `name`; 

# attr 유니크 인덱스 걸기
## 중복변수 생성금지
## 변수찾는 속도 최적화
ALTER TABLE `attr` ADD UNIQUE INDEX (`relTypeCode`, `relId`, `typeCode`, `type2Code`); 

## 특정 조건을 만족하는 회원 또는 게시물(기타 데이터)를 빠르게 찾기 위해서
ALTER TABLE `attr` ADD INDEX (`relTypeCode`, `typeCode`, `type2Code`);  