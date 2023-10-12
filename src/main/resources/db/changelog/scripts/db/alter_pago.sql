delete
from pago
where 1 = 1;
update deuda
set status='PENDING'
where 1 = 1;
update deuda
set saldo=monto_deuda
where 1 = 1;

alter table pago
    add column if not exists uuid varchar (50) not null;
