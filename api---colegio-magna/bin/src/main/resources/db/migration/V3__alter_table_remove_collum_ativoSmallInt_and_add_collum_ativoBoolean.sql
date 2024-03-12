ALTER TABLE professores DROP COLUMN ativo;
ALTER TABLE professores ADD ativo BOOLEAN;
UPDATE professores SET ativo = TRUE;
