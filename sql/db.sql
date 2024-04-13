use doggy;
-- 接口信息
create table if not exists doggy.`interface_info`
(
    `id` bigint not null auto_increment comment '主键' primary key,
    `name` varchar(256) not null comment '名称',
    `description` varchar(256) null comment '描述',
    `url` varchar(512) not null comment '接口地址',
    `requestParams` text not null comment '请求参数',
    `requestHeader` text null comment '请求头',
    `responseHeader` text null comment '响应头',
    `status` int default 0 not null comment '接口状态（0-关闭，1-开启））',
    `method` varchar(256) not null comment '请求类型',
    `userId` bigint not null comment '创建人',
    `createTime` datetime default CURRENT_TIMESTAMP not null comment '创建时间',
    `updateTime` datetime default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '更新时间',
    `isDelete` tinyint default 0 not null comment '是否删除(0-未删, 1-已删)'
) comment '接口信息';

alter table doggy.interface_info convert to charset utf8;

insert into doggy.`interface_info` (`name`, `description`, `url`, `requestHeader`, `responseHeader`, `status`, `method`, `userId`) values ('徐越彬', '侯思聪', 'www.lyndon-strosin.biz', '邓鹭洋', '刘风华', 0, '胡伟祺', 51413);
insert into doggy.`interface_info` (`name`, `description`, `url`, `requestHeader`, `responseHeader`, `status`, `method`, `userId`) values ('田擎苍', '邹思', 'www.mahalia-schinner.com', '陈靖琪', '孟懿轩', 0, '洪昊强', 29818960);
insert into doggy.`interface_info` (`name`, `description`, `url`, `requestHeader`, `responseHeader`, `status`, `method`, `userId`) values ('陈驰', '戴语堂', 'www.naomi-lind.com', '韦鹏涛', '曹乐驹', 0, '秦鹏飞', 91153);
insert into doggy.`interface_info` (`name`, `description`, `url`, `requestHeader`, `responseHeader`, `status`, `method`, `userId`) values ('洪建辉', '熊鹏煊', 'www.rickie-turcotte.net', '邓风华', '许钰轩', 0, '侯峻熙', 47016831);
insert into doggy.`interface_info` (`name`, `description`, `url`, `requestHeader`, `responseHeader`, `status`, `method`, `userId`) values ('段泽洋', '段笑愚', 'www.estell-wisozk.io', '秦潇然', '石瑞霖', 0, '魏修洁', 573);
insert into doggy.`interface_info` (`name`, `description`, `url`, `requestHeader`, `responseHeader`, `status`, `method`, `userId`) values ('毛修杰', '邹立诚', 'www.dino-corwin.co', '姚文昊', '郑靖琪', 0, '龙鹏涛', 97);
insert into doggy.`interface_info` (`name`, `description`, `url`, `requestHeader`, `responseHeader`, `status`, `method`, `userId`) values ('曹烨霖', '朱鹏涛', 'www.mitchel-emard.info', '夏瑞霖', '何哲瀚', 0, '侯聪健', 959028);
insert into doggy.`interface_info` (`name`, `description`, `url`, `requestHeader`, `responseHeader`, `status`, `method`, `userId`) values ('邱修洁', '郝旭尧', 'www.ping-stoltenberg.com', '白煜祺', '史烨伟', 0, '石越泽', 528);
insert into doggy.`interface_info` (`name`, `description`, `url`, `requestHeader`, `responseHeader`, `status`, `method`, `userId`) values ('黎晓啸', '王烨磊', 'www.newton-haley.info', '薛文', '沈航', 0, '金智辉', 89);
insert into doggy.`interface_info` (`name`, `description`, `url`, `requestHeader`, `responseHeader`, `status`, `method`, `userId`) values ('周明杰', '韦博文', 'www.willian-gulgowski.co', '蒋绍齐', '阎鑫磊', 0, '叶展鹏', 851007839);
insert into doggy.`interface_info` (`name`, `description`, `url`, `requestHeader`, `responseHeader`, `status`, `method`, `userId`) values ('董志泽', '赵天宇', 'www.cary-emard.biz', '秦嘉熙', '韩君浩', 0, '袁瑾瑜', 96594);
insert into doggy.`interface_info` (`name`, `description`, `url`, `requestHeader`, `responseHeader`, `status`, `method`, `userId`) values ('马峻熙', '徐天磊', 'www.hyacinth-haag.com', '顾烨霖', '方越泽', 0, '谭展鹏', 4);
insert into doggy.`interface_info` (`name`, `description`, `url`, `requestHeader`, `responseHeader`, `status`, `method`, `userId`) values ('邹皓轩', '万昊强', 'www.paul-rice.biz', '韩建辉', '何智渊', 0, '胡鑫鹏', 979775287);
insert into doggy.`interface_info` (`name`, `description`, `url`, `requestHeader`, `responseHeader`, `status`, `method`, `userId`) values ('严鹏', '袁烨伟', 'www.caleb-ward.biz', '程立诚', '陆驰', 0, '段浩', 4746040200);
insert into doggy.`interface_info` (`name`, `description`, `url`, `requestHeader`, `responseHeader`, `status`, `method`, `userId`) values ('白晟睿', '王瑾瑜', 'www.abel-witting.name', '崔苑博', '邹熠彤', 0, '莫聪健', 367);
insert into doggy.`interface_info` (`name`, `description`, `url`, `requestHeader`, `responseHeader`, `status`, `method`, `userId`) values ('谭擎苍', '邓楷瑞', 'www.mickey-dickens.biz', '王伟诚', '曾皓轩', 0, '高振家', 4);
insert into doggy.`interface_info` (`name`, `description`, `url`, `requestHeader`, `responseHeader`, `status`, `method`, `userId`) values ('徐旭尧', '侯鑫磊', 'www.corrin-stark.info', '蒋立轩', '田建辉', 0, '陶立轩', 4050);
insert into doggy.`interface_info` (`name`, `description`, `url`, `requestHeader`, `responseHeader`, `status`, `method`, `userId`) values ('李健雄', '曹锦程', 'www.lacy-monahan.biz', '徐思源', '金鹏飞', 0, '黄涛', 24666);
insert into doggy.`interface_info` (`name`, `description`, `url`, `requestHeader`, `responseHeader`, `status`, `method`, `userId`) values ('毛明辉', '任昊然', 'www.sherman-koepp.co', '卢浩然', '王炎彬', 0, '廖思源', 2);
insert into doggy.`interface_info` (`name`, `description`, `url`, `requestHeader`, `responseHeader`, `status`, `method`, `userId`) values ('武明杰', '钟博超', 'www.jennefer-harber.biz', '魏潇然', '戴鹏煊', 0, '崔智渊', 905);

-- 用户调用接口关系表
create table if not exists doggy.`user_interface_info`
(
    `id` bigint not null auto_increment comment  '主键' primary key,
    `userId` bigint not null comment '调用用户 id',
    `interfaceInfoId` bigint not null  comment '接口 id',
    `totalNum` int default 0 not null comment '总调用次数',
    `leftNum` int default 0 not null comment '剩余调用次数',
    `status` int default 0 not null comment '0-正常，1-禁用',
    `createTime` datetime default CURRENT_TIMESTAMP not null comment '创建时间',
    `updateTime` datetime default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '更新时间',
    `isDelete` tinyint default 0 not null comment '是否删除（0-未删，1-已删）'
)comment '用户调用接口关系';

alter table user
    add userProfile varchar(512) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '用户简介';
