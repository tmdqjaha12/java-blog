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
    memberId INT(10) UNSIGNED NOT NULL,
    displayStatus TINYINT(1) UNSIGNED NOT NULL,
    `title` CHAR(200) NOT NULL,
    `body` TEXT NOT NULL,
    hit INT(100) UNSIGNED NOT NULL
);

# 1번글 생성
INSERT INTO article
SET regDate = NOW()
, updateDate = NOW()
, title = '12341234'
, `body` = '12341234'
, cateItemId = 1
, memberId = 1
, displayStatus = 1;








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


DROP TABLE IF EXISTS `articleReply`;
CREATE TABLE `articleReply` (
    id INT(10) UNSIGNED NOT NULL PRIMARY KEY AUTO_INCREMENT,
    regDate DATETIME NOT NULL,
    updateDate DATETIME NOT NULL,
    articleId INT(10) NOT NULL,
    `body` TEXT NOT NULL,
    memberId INT(10) NOT NULL
);


INSERT INTO articleReply
SET regDate = NOW()
, updateDate = NOW()
, articleId = 1
, memberId = 1
, `body` = 'ㅎㅇ';

SELECT *
FROM articleReply;

SELECT COUNT(*)
FROM `member`
WHERE loginId = 'sbs';

SELECT COUNT(*)
FROM `member`
WHERE loginId = '12';

////////////

세션은 서버에 저장되는 각 사용자 별 개인 저장소 이다
HttpSession session = request.getSession();

로그인 처리 memberController의 doLogin에서 처리해야함
session.setAttribute("loginedMember", 1);


로그인 여부 체크
int loginedMemberId = 0;
if ( session.getAttribute("loginedMemberId")!=null){
    loginedMemberId = (int)session.getAttribute("loginedMemberId");
}

로그아웃
session.removeAttribute("loginedMemberId");