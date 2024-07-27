INSERT INTO `ca_menu` (`name`, `code`, `fullTextBody`, `createdDate`, `lastModifiedDate`, `createById`, `updateById`, `ownerId`, `isDeleted`) VALUES
('系统', 'system', '系统 system 1 1 1 false', '2024-07-24 12:48:42', NULL, 1, 1, 1, b'0');

INSERT INTO `ca_config` (`name`, `key`, `value`, `fullTextBody`, `createdDate`, `lastModifiedDate`, `createById`, `updateById`, `ownerId`, `isDeleted`) VALUES
('隐藏crudapi', 'loginHiddenCrudapi', 'false', '隐藏crudapi loginHiddenCrudapi false 1 1 1 false', '2024-07-23 16:56:28', NULL, 1, 1, 1, b'0');
COMMIT;
