-- Add apellido column to clientes table
ALTER TABLE clientes ADD COLUMN IF NOT EXISTS apellido VARCHAR(255);

-- Remove email unique constraint if exists
ALTER TABLE clientes DROP CONSTRAINT IF EXISTS uk1c96wv36rk2hwui7qhjks3mvg;
