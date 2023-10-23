update "Actuals" set promo = 'Promo' where id in
(select a.id from "Actuals" a inner join "Price" p on sales/volume != price and a.material_no = p.material_no and a.chain = p.chain);