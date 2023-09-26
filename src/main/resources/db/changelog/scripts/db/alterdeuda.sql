alter table deuda
    add column if not exists saldo numeric (10, 2);
alter table deuda
    add column if not exists status varchar (50);


delete
from pago
where 1 = 1;
update deuda
set status='PENDING'
where 1 = 1;
update deuda
set saldo=monto_deuda
where 1 = 1;