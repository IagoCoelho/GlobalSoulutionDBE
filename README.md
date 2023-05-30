# GlobalSoulutionDBE
APi para APP de cultivo, para analisar e mostrar que tipo de grão pode ser plantado.

# Endpoints
- Tipo solo
    - [Cadastrar](#cadastrar-tipo_solo)
    - Listar todos
    - Selecionar
    - Apagar
    - Atualizar
    - [Mostrar os detalhes](#detalhar-tipo_solo)
- Grao
    - [Cadastrar](#cadastrar-grao)
    - Listar todos
    - Apagar
    - Atualizar
    - [Mostrar os detalhes](#detalhar-grao)
- Tipo clima
    - [Cadastrar](#cadastrar-tipo_clima)
    - Listar todos
    - Apagar
    - Atualizar
    - [Mostrar os detalhes](#detalhar-tipo_clima)

---

### Cadastrar tipo solo
`POST` /globalSolution/api/tipo_solo

| campo | tipo | obrigatório | descrição
|-------|------|-------------|----------
| id_tipo_solo | INT | sim | é o id de um tipo de solo previamente cadastrado
| NM_cor_solo | String | sim | é a cor do solo com no máximo 255 caracteres
| NM_tipo_solo| String | sim | é o tipo de solo com no máximo 255 caracteres

**Exemplo de corpo do request**

```js
{
    "nm_cor_solo": "marrom"
    "nm_tipo_solo": "argiloso"
}
```

**Código de resposta**

| código | descrição
|--------|----------
| 201 | tipo de solo cadastrado com sucesso
| 400 | erro na validação dos dados da requisição

---

### Detalhar tipo solo
`GET` /globalSolution/api/tipo_solo{id}

**Exemplo de corpo de resposta**

```js
{
    "id_tipo_solo": 1,
    "nm_cor_solo": "beje",
    "nm_tipo_solo": "arenoso"
}
```

**Códigos de Resposta**

| código | descrição
|--------|----------|
| 200 | dados retornados no corpo da resposta
| 404 | não foi possível encontrar o tipo de solo com o id informado

---

### Cadastrar grao
`POST` /globalSolution/api/grao

| campo | tipo | obrigatório | descrição
|-------|------|-------------|----------
| id_grao | INT | sim | é o id de um grão previamente cadastrado
| NM_grao | String | sim | é o nome do grão
| DS_grao| String | sim | é a descrição do grão

**Exemplo de corpo do request**

```js
{
    "nm_grao": "soja"
    "ds_grao": "grão de soja"
}
```

**Código de resposta**

| código | descrição
|--------|----------
| 201 | grão cadastrado com sucesso
| 400 | erro na validação dos dados da requisição

---

### Detalhar grao
`GET` /globalSolution/api/grao{id}

**Exemplo de corpo de resposta**

```js
{
    "id_grao": 1,
    "nm_grao": "soja",
    "ds_grao": "grao de soja"
}
```

**Códigos de Resposta**

| código | descrição
|--------|----------|
| 200 | dados retornados no corpo da resposta
| 404 | não foi possível encontrar o grão com o id informado

---

### Cadastrar tipo clima
`POST` /globalSolution/api/tipo_clima

| campo | tipo | obrigatório | descrição
|-------|------|-------------|----------
| id_tipo_clima | INT | sim | é o id de um tipo de clima previamente cadastrado
| DS_clima | String | sim | é a descrição do clima com no máximo 255 caracteres

**Exemplo de corpo do request**

```js
{
    "DS_clima": "clima mais seco"
}
```

**Código de resposta**

| código | descrição
|--------|----------
| 201 | tipo de clima cadastrado com sucesso
| 400 | erro na validação dos dados da requisição

---

### Detalhar tipo clima
`GET` /globalSolution/api/tipo_clima{id}

**Exemplo de corpo de resposta**

```js
{
    "id_tipo_clima": 1,
    "ds_clima": "seco",
}
```

**Códigos de Resposta**

| código | descrição
|--------|----------|
| 200 | dados retornados no corpo da resposta
| 404 | não foi possível encontrar o tipo de clima com o id informado