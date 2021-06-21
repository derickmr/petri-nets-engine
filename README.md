# petri-nets-engine + modelsimulator

Esse repositório está dividido da seguinte forma:

- No diretório src/main/java/com/unisinos/petri está o código das Redes de Petri;
- No diretório src/main/java/com/unisinos/modelsimulator está o código da Engine de Simulação Orientada a Eventos.

Detalhes sobre a Engine de Simulação Orientada a Eventos:

- As classes Entity, EntitySet, Event, Resource e Scheduler são as principais classes do modelo, sendo essa última a responsável pelo gerenciamento e execução de todo o modelo (criação de entidades, execução de eventos, etc.);
- As classes EntitySet, Resource e Scheduler possuem diversos métodos de coleta de estatística;
- O modelo do Restaurante foi criado para validar a Engine, e pode ser encontrado em modelsimulator/restaurante;  
- Na pasta root do projeto tem 9 arquivos no formato .xlsx (caixa_1.xlsx, caixa_2.xlsx, etc.) que possuem a validação do modelo, validado usando o modelo do Anylogic como modelo real;
- O modelo pode ser executado de 4 formas diferentes:
  - Executar todos os eventos de vez (scheduler.simulate());
  - Executar os eventos em fatia de tempo (scheduler.simulateBy(tempo));  
  - Executar os eventos até um determinado tempo (scheduler.simulateUntil(tempo));
  - Executar passo por passo, como se fosse um debugger (scheduler.simulateStepByStep()).  

### Como executar o modelo do Restaurante? ###

- Abra o arquivo RestauranteMain.java e dê um click em Run na IDE;
- Por padrão, todos os eventos serão executados em uma única vez (scheduler.simulate());
- Entretanto, a classe RestauranteMain já possui o código exemplo para executar os demais modos de simulação. Esses métodos estão comentados;  
- Para trocar o método de execução (simulateUntil, simulateBy, simulateStepByStep), basta comentar a chamada de execução atual (scheduler.simulate()) e descomentar o código do modo de simulação que deseja executar.

  
