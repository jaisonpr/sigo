# SIGO - Back-end

Contém os módulos de backend da aplicação baseados na arquitetura de microsserviços. Para a POC foi escolhido o banco de dados H2, pela facilidade em fazer o drop-create em cada teste - há um script SQL para preparar minimamente a base de dados. Cada um dos módulos que fazem acesso ao repositório de dados possui sua própria instância do banco de dados H2.

## gestao-consultorias

Projeto responsável por cadastrar as empresas parceiras, além de realizar o relacionamento com as normas cadastradas no Módulo de Gestão de Normas e com os contratos vindos do ERP.

## gestao-normas

Projeto responsável por cadastrar as normas internas e externas utilizadas pela indústria. Deve também ocorrer a notificação ao responsável quando a norma for atualizada pelo órgão responsável.

## gestao-processos

Projeto responsável pela integração entre os outros módulos do sistema e dos sistemas externos. Este verifica o catálogo externo de normas e busca novos copntratos no ERP.


Para iniciar com o maven: ./start.sh