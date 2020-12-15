DROP TABLE MEMBER;

-- 회원
CREATE TABLE `MEMBER` (
	`ID`       VARCHAR(255)  NOT NULL COMMENT '아이디', -- 아이디
	`PASSWORD` CHAR(64)      NOT NULL COMMENT '암호화된 패스워드', -- 비밀번호
	`SALT`     VARCHAR(255)  NOT NULL COMMENT '솔트값', -- 솔트값
	`EMAIL`    VARCHAR(1000) NOT NULL COMMENT '이메일', -- 이메일
	`TYPE`     CHAR(1)       NOT NULL CHECK ( TYPE IN ('M', 'Y', 'N') ) COMMENT '관리자 : M / 활동회원: Y / 탈퇴회원 : N', -- 회원타입
	`REGDATE`  DATETIME      NOT NULL COMMENT '가입일', -- 가입일
	`DROPDATE` DATETIME      NULL     COMMENT '탈퇴일' -- 탈퇴일
)
COMMENT '회원';

-- 회원
ALTER TABLE `MEMBER`
	ADD CONSTRAINT `PK_MEMBER` -- 회원 기본키
		PRIMARY KEY (
			`ID` -- 아이디
		);