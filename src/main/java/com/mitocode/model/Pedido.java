package com.mitocode.model;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
--- FORMATO JSON EN POSTMAN ---
{
"listA" : ["POLLO","AGUACATE","TOMATE","CELULAR","MOCHILA","PC","BOCINA","MOCHILA","POLLO","HUEVO","TOMATE"],
"listB" : ["TOMATE","MOCHILA"]
}
--- SALIDA POR CONSOLA ---

[TOMATE, MOCHILA, MOCHILA, TOMATE]
{TOMATE=2, MOCHILA=2}
*/

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Pedido {
	
	List<String> listA; //= Arrays.asList("POLLO","AGUACATE","TOMATE","CELULAR","MOCHILA","PC","BOCINA","MOCHILA","POLLO","HUEVO","TOMATE");
	List<String> listB; //= Arrays.asList("TOMATE","MOCHILA");

}
