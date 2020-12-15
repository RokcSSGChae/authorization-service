DROP TABLE member;

-- 회원
CREATE TABLE `member` (
	`id`       VARCHAR(255)  NOT NULL COMMENT '아이디', -- 아이디
	`password` CHAR(64)      NOT NULL COMMENT '암호화된 패스워드', -- 비밀번호
	`salt`     VARCHAR(255)  NOT NULL COMMENT '솔트값', -- 솔트값
	`email`    VARCHAR(1000) NOT NULL COMMENT '이메일', -- 이메일
	`type`     CHAR(1)       NOT NULL CHECK ( TYPE IN ('M', 'Y', 'N') ) COMMENT '관리자 : M / 활동회원: Y / 탈퇴회원 : N', -- 회원타입
	`register_date`  DATETIME      NOT NULL COMMENT '가입일', -- 가입일
	`modified_date` DATETIME     NOT NULL     COMMENT '수정일' -- 수정일
)
COMMENT '회원';

-- 회원
ALTER TABLE `member`
	ADD CONSTRAINT `PK_MEMBER` -- 회원 기본키
		PRIMARY KEY (
			`id` -- 아이디
		);