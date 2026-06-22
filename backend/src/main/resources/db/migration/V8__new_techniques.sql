-- Global techniques
-- created_by IS NULL = global technique/exercise, cannot be modified or deleted by users

CREATE EXTENSION IF NOT EXISTS pgcrypto;

-- =========================================================
-- JIU_JITSU
-- =========================================================

INSERT INTO techniques (id, name, sport_type, category, description, created_at, updated_at)
SELECT
    gen_random_uuid(),
    v.name,
    v.sport_type,
    v.category,
    v.description,
    NOW(),
    NOW()
FROM (
    VALUES
        -- Submissions
        ('Armbar', 'JIU_JITSU', 'SUBMISSION', 'Chave de braço aplicada estendendo o cotovelo do oponente.'),
        ('Triângulo', 'JIU_JITSU', 'SUBMISSION', 'Finalização com as pernas envolvendo o pescoço e um braço do oponente.'),
        ('Kimura', 'JIU_JITSU', 'SUBMISSION', 'Chave de ombro aplicada com controle de dois pontos no braço.'),
        ('Americana', 'JIU_JITSU', 'SUBMISSION', 'Chave de ombro aplicada geralmente da montada ou do cem quilos.'),
        ('Omoplata', 'JIU_JITSU', 'SUBMISSION', 'Finalização de ombro usando as pernas para controlar e torcer o braço do oponente.'),
        ('Rear Naked Choke', 'JIU_JITSU', 'SUBMISSION', 'Estrangulamento pelas costas com o antebraço pressionando a carótida.'),
        ('Mata Leão', 'JIU_JITSU', 'SUBMISSION', 'Estrangulamento pelas costas utilizando controle do pescoço e fechamento do braço.'),
        ('Ezekiel Choke', 'JIU_JITSU', 'SUBMISSION', 'Estrangulamento aplicado com a manga do kimono ou com o punho.'),
        ('Guilhotina', 'JIU_JITSU', 'SUBMISSION', 'Estrangulamento frontal envolvendo o pescoço do oponente com os braços.'),
        ('Katagatame', 'JIU_JITSU', 'SUBMISSION', 'Estrangulamento usando braço e ombro para comprimir o pescoço do oponente.'),
        ('D''Arce Choke', 'JIU_JITSU', 'SUBMISSION', 'Estrangulamento aplicado com o braço passando por baixo da axila e fechando no pescoço.'),
        ('Anaconda Choke', 'JIU_JITSU', 'SUBMISSION', 'Estrangulamento a partir do controle frontal, geralmente associado a rolamento.'),
        ('North South Choke', 'JIU_JITSU', 'SUBMISSION', 'Estrangulamento aplicado da posição norte-sul com pressão no pescoço.'),
        ('Baseball Bat Choke', 'JIU_JITSU', 'SUBMISSION', 'Estrangulamento usando pegadas semelhantes à empunhadura de um taco de beisebol.'),
        ('Bow and Arrow Choke', 'JIU_JITSU', 'SUBMISSION', 'Estrangulamento pelas costas usando lapela e controle da perna ou quadril.'),
        ('Cross Collar Choke', 'JIU_JITSU', 'SUBMISSION', 'Estrangulamento com pegadas cruzadas na gola do kimono.'),
        ('Loop Choke', 'JIU_JITSU', 'SUBMISSION', 'Estrangulamento com lapela aplicado geralmente contra postura baixa do oponente.'),
        ('Relógio', 'JIU_JITSU', 'SUBMISSION', 'Estrangulamento aplicado contra oponente em quatro apoios usando a gola.'),
        ('Chave de Pé Reta', 'JIU_JITSU', 'SUBMISSION', 'Finalização no tornozelo com extensão controlada do pé.'),
        ('Toe Hold', 'JIU_JITSU', 'SUBMISSION', 'Finalização no pé com torção controlada do tornozelo.'),
        ('Kneebar', 'JIU_JITSU', 'SUBMISSION', 'Finalização na perna por hiperextensão do joelho.'),
        ('Wrist Lock', 'JIU_JITSU', 'SUBMISSION', 'Finalização no punho por flexão ou torção controlada da articulação.'),

        -- Takedowns
        ('Tomoe Nage', 'JIU_JITSU', 'TAKEDOWN', 'Projeção sacrifício usando os pés no quadril do oponente.'),
        ('Double Leg', 'JIU_JITSU', 'TAKEDOWN', 'Queda dupla atacando as duas pernas do oponente.'),
        ('Single Leg', 'JIU_JITSU', 'TAKEDOWN', 'Queda atacando uma das pernas do oponente com controle de postura.'),
        ('Ankle Pick', 'JIU_JITSU', 'TAKEDOWN', 'Queda atacando o tornozelo do oponente enquanto quebra sua postura.'),
        ('Body Lock Takedown', 'JIU_JITSU', 'TAKEDOWN', 'Queda usando controle fechado ao redor do tronco do oponente.'),
        ('Osoto Gari', 'JIU_JITSU', 'TAKEDOWN', 'Projeção de judô com varrida externa na perna do oponente.'),
        ('Ouchi Gari', 'JIU_JITSU', 'TAKEDOWN', 'Projeção com varrida interna na perna do oponente.'),
        ('Kouchi Gari', 'JIU_JITSU', 'TAKEDOWN', 'Pequena varrida interna usada para desequilibrar e projetar o oponente.'),
        ('Uchi Mata', 'JIU_JITSU', 'TAKEDOWN', 'Projeção com entrada de quadril e elevação da perna interna.'),
        ('Seoi Nage', 'JIU_JITSU', 'TAKEDOWN', 'Projeção de ombro carregando o oponente sobre as costas.'),
        ('Sumi Gaeshi', 'JIU_JITSU', 'TAKEDOWN', 'Projeção sacrifício usando gancho interno para elevar e girar o oponente.'),
        ('De Ashi Barai', 'JIU_JITSU', 'TAKEDOWN', 'Varrida no pé avançado do oponente durante o deslocamento.'),
        ('Arm Drag to Back Take', 'JIU_JITSU', 'TAKEDOWN', 'Entrada puxando o braço para expor as costas e progredir para domínio.'),
        ('Snap Down', 'JIU_JITSU', 'TAKEDOWN', 'Quebra de postura puxando cabeça e ombros para baixo, abrindo ataque frontal.'),

        -- Guards
        ('Guard Pull', 'JIU_JITSU', 'GUARD', 'Técnica de puxar o oponente para a guarda.'),
        ('Closed Guard', 'JIU_JITSU', 'GUARD', 'Guarda fechada com os calcanhares cruzados nas costas do oponente.'),
        ('Open Guard', 'JIU_JITSU', 'GUARD', 'Guarda aberta usando pernas e pegadas para controlar distância e desequilíbrio.'),
        ('Half Guard', 'JIU_JITSU', 'GUARD', 'Guarda em que uma perna do oponente fica presa entre as pernas do atleta por baixo.'),
        ('Deep Half Guard', 'JIU_JITSU', 'GUARD', 'Meia-guarda profunda com entrada sob o centro de gravidade do oponente.'),
        ('Knee Shield Guard', 'JIU_JITSU', 'GUARD', 'Meia-guarda com joelho fazendo escudo para controlar distância.'),
        ('Butterfly Guard', 'JIU_JITSU', 'GUARD', 'Guarda borboleta usando ganchos internos para elevar e desequilibrar o oponente.'),
        ('X-Guard', 'JIU_JITSU', 'GUARD', 'Guarda em X com controle das pernas do oponente de baixo.'),
        ('Single Leg X-Guard', 'JIU_JITSU', 'GUARD', 'Guarda de uma perna em X controlando quadril, joelho e tornozelo.'),
        ('Spider Guard', 'JIU_JITSU', 'GUARD', 'Guarda com os pés nos bíceps do oponente e controle dos punhos.'),
        ('Lasso Guard', 'JIU_JITSU', 'GUARD', 'Guarda com a perna enrolada no braço do oponente usando controle de manga.'),
        ('De La Riva Guard', 'JIU_JITSU', 'GUARD', 'Guarda com gancho externo na perna do oponente e controle de distância.'),
        ('Reverse De La Riva Guard', 'JIU_JITSU', 'GUARD', 'Guarda com gancho invertido na perna do oponente, comum contra passagens em pé.'),
        ('50/50 Guard', 'JIU_JITSU', 'GUARD', 'Guarda com as pernas entrelaçadas em posição simétrica de controle de perna.'),
        ('Lapel Guard', 'JIU_JITSU', 'GUARD', 'Guarda usando lapela para controle adicional de postura e distância.'),
        ('Worm Guard', 'JIU_JITSU', 'GUARD', 'Guarda de lapela com controle avançado da perna e postura do oponente.'),

        -- Sweeps
        ('Scissor Sweep', 'JIU_JITSU', 'SWEEP', 'Raspagem em tesoura executada da guarda fechada.'),
        ('Hip Bump Sweep', 'JIU_JITSU', 'SWEEP', 'Raspagem projetando o quadril para frente a partir da guarda fechada.'),
        ('Flower Sweep', 'JIU_JITSU', 'SWEEP', 'Raspagem da guarda fechada controlando braço e perna do oponente.'),
        ('Pendulum Sweep', 'JIU_JITSU', 'SWEEP', 'Raspagem usando balanço da perna e controle lateral do corpo do oponente.'),
        ('Lumberjack Sweep', 'JIU_JITSU', 'SWEEP', 'Raspagem atacando a base do oponente quando ele levanta na guarda fechada.'),
        ('Butterfly Sweep', 'JIU_JITSU', 'SWEEP', 'Raspagem da guarda borboleta usando gancho e elevação do quadril.'),
        ('X-Guard Sweep', 'JIU_JITSU', 'SWEEP', 'Raspagem a partir da guarda X controlando as pernas do oponente.'),
        ('Single Leg X Sweep', 'JIU_JITSU', 'SWEEP', 'Raspagem da single leg X desequilibrando o oponente para trás ou lateralmente.'),
        ('De La Riva Sweep', 'JIU_JITSU', 'SWEEP', 'Raspagem usando o gancho De La Riva e controle de pegadas.'),
        ('Lasso Sweep', 'JIU_JITSU', 'SWEEP', 'Raspagem usando controle de laço no braço e desequilíbrio lateral.'),
        ('Old School Sweep', 'JIU_JITSU', 'SWEEP', 'Raspagem clássica da meia-guarda profunda controlando a perna do oponente.'),
        ('Waiter Sweep', 'JIU_JITSU', 'SWEEP', 'Raspagem da meia-guarda profunda elevando a perna do oponente.'),
        ('Tripod Sweep', 'JIU_JITSU', 'SWEEP', 'Raspagem da guarda aberta controlando tornozelo e quadril do oponente.'),
        ('Balloon Sweep', 'JIU_JITSU', 'SWEEP', 'Raspagem elevando o oponente com as pernas a partir da guarda aberta.'),

        -- Passes
        ('Torreando', 'JIU_JITSU', 'PASS', 'Passagem de guarda circulando pelas pernas sem agarrá-las diretamente.'),
        ('Knee Slice Pass', 'JIU_JITSU', 'PASS', 'Passagem de guarda cortando com o joelho entre as pernas do oponente.'),
        ('Over Under Pass', 'JIU_JITSU', 'PASS', 'Passagem com um braço por cima e outro por baixo das pernas do oponente.'),
        ('Double Under Pass', 'JIU_JITSU', 'PASS', 'Passagem controlando as duas pernas por baixo para empilhar o oponente.'),
        ('Stack Pass', 'JIU_JITSU', 'PASS', 'Passagem empilhando o quadril do oponente sobre os ombros.'),
        ('Leg Drag Pass', 'JIU_JITSU', 'PASS', 'Passagem arrastando a perna do oponente para cruzar sua linha de quadril.'),
        ('Body Lock Pass', 'JIU_JITSU', 'PASS', 'Passagem com controle fechado no tronco para limitar o quadril do oponente.'),
        ('X Pass', 'JIU_JITSU', 'PASS', 'Passagem rápida em pé desviando as pernas do oponente lateralmente.'),
        ('Long Step Pass', 'JIU_JITSU', 'PASS', 'Passagem com passo longo para trás, comum contra meia-guarda e De La Riva.'),
        ('Smash Pass', 'JIU_JITSU', 'PASS', 'Passagem esmagando as pernas do oponente para limitar recomposição.'),
        ('Headquarters Pass', 'JIU_JITSU', 'PASS', 'Sistema de passagem em pé controlando uma perna entre as pernas do passador.'),
        ('São Paulo Pass', 'JIU_JITSU', 'PASS', 'Passagem da guarda fechada com pressão e abertura progressiva da guarda.'),

        -- Controls
        ('Mount Control', 'JIU_JITSU', 'CONTROL', 'Controle da montada com domínio do quadril e tronco do oponente.'),
        ('Side Control', 'JIU_JITSU', 'CONTROL', 'Controle lateral estabilizando o tronco do oponente no chão.'),
        ('North South Control', 'JIU_JITSU', 'CONTROL', 'Controle norte-sul com pressão sobre tronco e cabeça do oponente.'),
        ('Knee on Belly', 'JIU_JITSU', 'CONTROL', 'Controle com joelho sobre o abdômen ou linha do quadril do oponente.'),
        ('Back Control', 'JIU_JITSU', 'CONTROL', 'Controle das costas usando ganchos, cinto de segurança ou body triangle.'),
        ('Turtle Control', 'JIU_JITSU', 'CONTROL', 'Controle sobre o oponente em quatro apoios, buscando costas ou estrangulamentos.'),
        ('S-Mount', 'JIU_JITSU', 'CONTROL', 'Variação da montada com joelho alto para atacar braço ou estrangulamento.'),
        ('Kesa Gatame', 'JIU_JITSU', 'CONTROL', 'Controle lateral com domínio de cabeça e braço, adaptado do judô.'),

        -- Escapes / Defense
        ('Shrimp Escape', 'JIU_JITSU', 'ESCAPE', 'Movimento de fuga de quadril usado para criar espaço e recompor guarda.'),
        ('Bridge and Roll Escape', 'JIU_JITSU', 'ESCAPE', 'Fuga da montada usando ponte e rotação para inverter a posição.'),
        ('Elbow Escape', 'JIU_JITSU', 'ESCAPE', 'Fuga da montada usando cotovelos e quadril para recuperar meia-guarda ou guarda.'),
        ('Side Control Escape', 'JIU_JITSU', 'ESCAPE', 'Fuga do cem quilos criando espaço entre quadril, ombro e cabeça.'),
        ('Back Escape', 'JIU_JITSU', 'ESCAPE', 'Fuga das costas removendo ganchos e protegendo o pescoço.'),
        ('Technical Stand Up', 'JIU_JITSU', 'ESCAPE', 'Levantada técnica mantendo distância e proteção contra avanço do oponente.'),
        ('Granby Roll', 'JIU_JITSU', 'ESCAPE', 'Rolamento usado para escapar, inverter ou recompor guarda.'),
        ('Turtle Escape', 'JIU_JITSU', 'ESCAPE', 'Saída da posição de quatro apoios evitando controle das costas.'),
        ('Submission Defense', 'JIU_JITSU', 'DEFENSE', 'Defesa geral contra tentativas de finalização, priorizando postura, alinhamento e retirada segura.'),
        ('Guard Retention', 'JIU_JITSU', 'DEFENSE', 'Defesa de passagem usando enquadramento, quadril e reposicionamento das pernas.'),

        -- Transitions
        ('Berimbolo', 'JIU_JITSU', 'TRANSITION', 'Transição invertida geralmente a partir da De La Riva para buscar as costas.'),
        ('Arm Drag', 'JIU_JITSU', 'TRANSITION', 'Puxada de braço para criar ângulo e acessar costas ou queda.'),
        ('Hip Switch', 'JIU_JITSU', 'TRANSITION', 'Troca de quadril usada para estabilizar passagens e controles.'),
        ('Knee Cut to Mount', 'JIU_JITSU', 'TRANSITION', 'Transição da passagem knee slice para a montada.'),
        ('Side Control to Mount', 'JIU_JITSU', 'TRANSITION', 'Progressão do cem quilos para a montada controlando quadril e joelhos.')
) AS v(name, sport_type, category, description)
WHERE NOT EXISTS (
    SELECT 1
    FROM techniques t
    WHERE LOWER(t.name) = LOWER(v.name)
      AND t.sport_type = v.sport_type
      AND t.created_by IS NULL
);

-- =========================================================
-- STRENGTH_TRAINING
-- =========================================================

INSERT INTO techniques (id, name, sport_type, category, description, created_at, updated_at)
SELECT
    gen_random_uuid(),
    v.name,
    v.sport_type,
    v.category,
    v.description,
    NOW(),
    NOW()
FROM (
    VALUES
        -- Chest / Push
        ('Supino Reto', 'STRENGTH_TRAINING', 'PUSH', 'Exercício de empurrar com barra no banco plano, foco no peitoral.'),
        ('Supino Reto com Halteres', 'STRENGTH_TRAINING', 'PUSH', 'Variação do supino reto com halteres, permitindo maior amplitude de movimento.'),
        ('Supino Inclinado', 'STRENGTH_TRAINING', 'PUSH', 'Variação do supino com banco inclinado, enfatizando a porção superior do peitoral.'),
        ('Supino Inclinado com Halteres', 'STRENGTH_TRAINING', 'PUSH', 'Supino inclinado usando halteres para maior estabilidade e amplitude.'),
        ('Supino Declinado', 'STRENGTH_TRAINING', 'PUSH', 'Variação do supino com banco declinado, enfatizando a porção inferior do peitoral.'),
        ('Supino Máquina', 'STRENGTH_TRAINING', 'PUSH', 'Exercício de empurrar em máquina guiada, com maior estabilidade.'),
        ('Crucifixo com Halteres', 'STRENGTH_TRAINING', 'PUSH', 'Exercício de isolamento para peitoral com halteres em movimento de adução.'),
        ('Crucifixo Inclinado', 'STRENGTH_TRAINING', 'PUSH', 'Variação do crucifixo em banco inclinado para enfatizar a parte superior do peitoral.'),
        ('Crucifixo na Máquina', 'STRENGTH_TRAINING', 'PUSH', 'Isolamento de peitoral em máquina, também conhecido como peck deck.'),
        ('Crossover na Polia', 'STRENGTH_TRAINING', 'PUSH', 'Exercício de adução dos braços na polia, focado no peitoral.'),
        ('Crossover Alto', 'STRENGTH_TRAINING', 'PUSH', 'Variação do crossover partindo da polia alta, enfatizando fibras inferiores do peitoral.'),
        ('Crossover Baixo', 'STRENGTH_TRAINING', 'PUSH', 'Variação do crossover partindo da polia baixa, enfatizando fibras superiores do peitoral.'),
        ('Flexão de Braço', 'STRENGTH_TRAINING', 'PUSH', 'Exercício com peso corporal para peitoral, ombros, tríceps e core.'),
        ('Paralelas para Peito', 'STRENGTH_TRAINING', 'PUSH', 'Mergulho nas paralelas com inclinação do tronco para maior foco no peitoral.'),

        -- Shoulders / Push
        ('Desenvolvimento Militar', 'STRENGTH_TRAINING', 'PUSH', 'Empurrada vertical com barra, foco em deltoides e tríceps.'),
        ('Desenvolvimento com Halteres', 'STRENGTH_TRAINING', 'PUSH', 'Empurrada vertical com halteres, exigindo estabilidade dos ombros.'),
        ('Desenvolvimento Máquina', 'STRENGTH_TRAINING', 'PUSH', 'Exercício de ombros em máquina guiada com maior controle de trajetória.'),
        ('Arnold Press', 'STRENGTH_TRAINING', 'PUSH', 'Variação de desenvolvimento com rotação dos halteres durante o movimento.'),
        ('Elevação Lateral', 'STRENGTH_TRAINING', 'PUSH', 'Isolamento do deltoide lateral com halteres ou polia.'),
        ('Elevação Lateral na Polia', 'STRENGTH_TRAINING', 'PUSH', 'Variação da elevação lateral com tensão contínua na polia.'),
        ('Elevação Frontal', 'STRENGTH_TRAINING', 'PUSH', 'Isolamento do deltoide anterior elevando a carga à frente do corpo.'),
        ('Crucifixo Inverso', 'STRENGTH_TRAINING', 'PULL', 'Exercício para deltoide posterior e parte superior das costas.'),
        ('Face Pull', 'STRENGTH_TRAINING', 'PULL', 'Puxada na polia em direção ao rosto, foco em deltoide posterior e estabilidade escapular.'),
        ('Remada Alta', 'STRENGTH_TRAINING', 'PULL', 'Puxada vertical próxima ao corpo, trabalhando trapézio e deltoides.'),
        ('Encolhimento', 'STRENGTH_TRAINING', 'PULL', 'Elevação dos ombros com carga, foco em trapézio superior.'),

        -- Back / Pull
        ('Levantamento Terra', 'STRENGTH_TRAINING', 'FULL_BODY', 'Movimento composto puxando a barra do chão até a extensão total do quadril.'),
        ('Levantamento Terra Sumô', 'STRENGTH_TRAINING', 'FULL_BODY', 'Variação do terra com base mais aberta, maior participação de quadril e adutores.'),
        ('Remada Curvada', 'STRENGTH_TRAINING', 'PULL', 'Puxada com tronco inclinado, foco em dorsais, romboides e trapézio.'),
        ('Remada Unilateral com Halter', 'STRENGTH_TRAINING', 'PULL', 'Remada com um braço apoiado, foco em dorsais e controle escapular.'),
        ('Remada Baixa', 'STRENGTH_TRAINING', 'PULL', 'Remada sentada na polia, foco em dorsais e meio das costas.'),
        ('Remada Cavalinho', 'STRENGTH_TRAINING', 'PULL', 'Remada com barra apoiada ou máquina, excelente para espessura das costas.'),
        ('Remada Máquina', 'STRENGTH_TRAINING', 'PULL', 'Remada guiada em máquina, permitindo controle e estabilidade.'),
        ('Remada Serrote', 'STRENGTH_TRAINING', 'PULL', 'Remada unilateral com halter, comum para dorsais e controle de escápula.'),
        ('Puxada Frente', 'STRENGTH_TRAINING', 'PULL', 'Puxada na polia alta pela frente, foco em dorsais.'),
        ('Puxada Aberta', 'STRENGTH_TRAINING', 'PULL', 'Puxada na polia com pegada aberta para enfatizar dorsais.'),
        ('Puxada Neutra', 'STRENGTH_TRAINING', 'PULL', 'Puxada na polia com pegada neutra, confortável para ombros e cotovelos.'),
        ('Puxada Triângulo', 'STRENGTH_TRAINING', 'PULL', 'Puxada na polia com pegador triângulo, foco em dorsais e bíceps.'),
        ('Pulldown com Braços Retos', 'STRENGTH_TRAINING', 'PULL', 'Exercício de isolamento para dorsais mantendo os braços quase estendidos.'),
        ('Pullover', 'STRENGTH_TRAINING', 'PULL', 'Movimento de extensão do ombro com foco em dorsais e serrátil.'),
        ('Pull-up', 'STRENGTH_TRAINING', 'PULL', 'Barra fixa com pegada pronada, foco em dorsais e bíceps.'),
        ('Barra Fixa Supinada', 'STRENGTH_TRAINING', 'PULL', 'Barra fixa com pegada supinada, aumentando participação do bíceps.'),
        ('Barra Fixa Neutra', 'STRENGTH_TRAINING', 'PULL', 'Barra fixa com pegada neutra, boa alternativa para reduzir desconforto nos ombros.'),

        -- Biceps / Pull
        ('Rosca Direta', 'STRENGTH_TRAINING', 'PULL', 'Flexão de cotovelo com barra, foco em bíceps braquial.'),
        ('Rosca Alternada', 'STRENGTH_TRAINING', 'PULL', 'Rosca com halteres alternando os braços durante a execução.'),
        ('Rosca Martelo', 'STRENGTH_TRAINING', 'PULL', 'Rosca com pegada neutra, foco em braquial e braquiorradial.'),
        ('Rosca Scott', 'STRENGTH_TRAINING', 'PULL', 'Rosca apoiada no banco Scott, reduzindo compensações do tronco.'),
        ('Rosca Concentrada', 'STRENGTH_TRAINING', 'PULL', 'Rosca unilateral com foco em controle e contração do bíceps.'),
        ('Rosca na Polia', 'STRENGTH_TRAINING', 'PULL', 'Flexão de cotovelo na polia, mantendo tensão contínua no bíceps.'),
        ('Rosca Inversa', 'STRENGTH_TRAINING', 'PULL', 'Rosca com pegada pronada, foco em antebraço e braquiorradial.'),

        -- Triceps / Push
        ('Tríceps Testa', 'STRENGTH_TRAINING', 'PUSH', 'Extensão de cotovelo deitado, foco nas cabeças do tríceps.'),
        ('Tríceps Francês', 'STRENGTH_TRAINING', 'PUSH', 'Extensão de cotovelo acima da cabeça, enfatizando a cabeça longa do tríceps.'),
        ('Tríceps Corda', 'STRENGTH_TRAINING', 'PUSH', 'Extensão de cotovelo na polia usando corda, com boa contração final.'),
        ('Tríceps Barra Reta', 'STRENGTH_TRAINING', 'PUSH', 'Extensão de cotovelo na polia com barra reta.'),
        ('Tríceps Barra V', 'STRENGTH_TRAINING', 'PUSH', 'Extensão de cotovelo na polia com barra V, pegada mais confortável.'),
        ('Tríceps Coice', 'STRENGTH_TRAINING', 'PUSH', 'Extensão de cotovelo com tronco inclinado, isolando tríceps.'),
        ('Tríceps Mergulho no Banco', 'STRENGTH_TRAINING', 'PUSH', 'Mergulho com apoio no banco, trabalhando tríceps e ombros.'),
        ('Paralelas para Tríceps', 'STRENGTH_TRAINING', 'PUSH', 'Mergulho nas paralelas com tronco mais vertical, enfatizando tríceps.'),
        ('Supino Fechado', 'STRENGTH_TRAINING', 'PUSH', 'Variação do supino com pegada fechada, maior foco em tríceps.'),

        -- Legs
        ('Agachamento Livre', 'STRENGTH_TRAINING', 'LEGS', 'Exercício fundamental de membros inferiores com barra nas costas.'),
        ('Agachamento Frontal', 'STRENGTH_TRAINING', 'LEGS', 'Variação do agachamento com barra à frente, maior demanda de quadríceps e core.'),
        ('Agachamento Sumô', 'STRENGTH_TRAINING', 'LEGS', 'Agachamento com base ampla, enfatizando adutores e glúteos.'),
        ('Hack Squat', 'STRENGTH_TRAINING', 'LEGS', 'Agachamento em máquina guiada, com foco em quadríceps.'),
        ('Leg Press', 'STRENGTH_TRAINING', 'LEGS', 'Extensão de membros inferiores no aparelho, com menor carga axial que o agachamento.'),
        ('Leg Press 45', 'STRENGTH_TRAINING', 'LEGS', 'Variação do leg press em plataforma inclinada a 45 graus.'),
        ('Cadeira Extensora', 'STRENGTH_TRAINING', 'LEGS', 'Isolamento do quadríceps no aparelho de extensão.'),
        ('Mesa Flexora', 'STRENGTH_TRAINING', 'LEGS', 'Isolamento dos isquiotibiais no aparelho de flexão.'),
        ('Cadeira Flexora', 'STRENGTH_TRAINING', 'LEGS', 'Flexão de joelhos sentado, focada em posteriores de coxa.'),
        ('Stiff', 'STRENGTH_TRAINING', 'LEGS', 'Variação de levantamento com foco em isquiotibiais e glúteos.'),
        ('Levantamento Terra Romeno', 'STRENGTH_TRAINING', 'LEGS', 'Movimento de dobradiça de quadril com ênfase em posteriores e glúteos.'),
        ('Afundo', 'STRENGTH_TRAINING', 'LEGS', 'Exercício unilateral para quadríceps, glúteos e estabilidade.'),
        ('Passada', 'STRENGTH_TRAINING', 'LEGS', 'Variação dinâmica do afundo, trabalhando força unilateral e equilíbrio.'),
        ('Agachamento Búlgaro', 'STRENGTH_TRAINING', 'LEGS', 'Exercício unilateral com pé traseiro elevado, excelente para quadríceps e glúteos.'),
        ('Hip Thrust', 'STRENGTH_TRAINING', 'LEGS', 'Extensão de quadril com apoio em banco, foco em glúteos.'),
        ('Glute Bridge', 'STRENGTH_TRAINING', 'LEGS', 'Ponte de glúteos no solo para extensão de quadril.'),
        ('Cadeira Abdutora', 'STRENGTH_TRAINING', 'LEGS', 'Exercício de abdução do quadril, foco em glúteo médio.'),
        ('Cadeira Adutora', 'STRENGTH_TRAINING', 'LEGS', 'Exercício de adução do quadril, foco nos adutores.'),
        ('Panturrilha em Pé', 'STRENGTH_TRAINING', 'LEGS', 'Elevação de calcanhares em pé, foco em gastrocnêmio.'),
        ('Panturrilha Sentado', 'STRENGTH_TRAINING', 'LEGS', 'Elevação de calcanhares sentado, maior foco no sóleo.'),

        -- Core
        ('Prancha', 'STRENGTH_TRAINING', 'CORE', 'Isometria de core em posição de prancha frontal.'),
        ('Prancha Lateral', 'STRENGTH_TRAINING', 'CORE', 'Isometria lateral para oblíquos e estabilizadores do tronco.'),
        ('Abdominal Crunch', 'STRENGTH_TRAINING', 'CORE', 'Flexão de tronco com foco no reto abdominal.'),
        ('Abdominal Infra', 'STRENGTH_TRAINING', 'CORE', 'Elevação de quadril ou pernas com foco na porção inferior do abdômen.'),
        ('Elevação de Pernas', 'STRENGTH_TRAINING', 'CORE', 'Exercício para abdômen e flexores do quadril elevando as pernas.'),
        ('Abdominal na Polia', 'STRENGTH_TRAINING', 'CORE', 'Flexão de tronco ajoelhado usando resistência da polia.'),
        ('Pallof Press', 'STRENGTH_TRAINING', 'CORE', 'Exercício anti-rotação na polia ou elástico para estabilidade de core.')
) AS v(name, sport_type, category, description)
WHERE NOT EXISTS (
    SELECT 1
    FROM techniques t
    WHERE LOWER(t.name) = LOWER(v.name)
      AND t.sport_type = v.sport_type
      AND t.created_by IS NULL
);

-- =========================================================
-- FUNCTIONAL_TRAINING
-- =========================================================

INSERT INTO techniques (id, name, sport_type, category, description, created_at, updated_at)
SELECT
    gen_random_uuid(),
    v.name,
    v.sport_type,
    v.category,
    v.description,
    NOW(),
    NOW()
FROM (
    VALUES
        ('Burpee', 'FUNCTIONAL_TRAINING', 'HIIT', 'Movimento completo: agachamento, prancha, flexão e salto.'),
        ('Box Jump', 'FUNCTIONAL_TRAINING', 'PLYOMETRICS', 'Salto em caixa desenvolvendo potência de membros inferiores.'),
        ('Kettlebell Swing', 'FUNCTIONAL_TRAINING', 'FULL_BODY', 'Balanço do kettlebell com extensão explosiva do quadril.'),
        ('Turkish Get-Up', 'FUNCTIONAL_TRAINING', 'BALANCE', 'Movimento complexo do solo até em pé com carga acima da cabeça.'),
        ('Prancha', 'FUNCTIONAL_TRAINING', 'CORE', 'Isometria de core em posição de prancha frontal.'),
        ('Mountain Climber', 'FUNCTIONAL_TRAINING', 'CONDITIONING', 'Simulação de escalada no chão com alternância rápida de joelhos.'),
        ('Thruster', 'FUNCTIONAL_TRAINING', 'FULL_BODY', 'Combinação de agachamento frontal com desenvolvimento.'),
        ('Battle Rope', 'FUNCTIONAL_TRAINING', 'CONDITIONING', 'Ondulações com cordas pesadas para condicionamento metabólico.'),
        ('Wall Ball', 'FUNCTIONAL_TRAINING', 'FULL_BODY', 'Agachamento com arremesso de bola na parede, combinando força e potência.'),
        ('Medicine Ball Slam', 'FUNCTIONAL_TRAINING', 'FULL_BODY', 'Arremesso explosivo da bola medicinal ao chão, foco em potência, core e coordenação.'),
        ('Farmer Walk', 'FUNCTIONAL_TRAINING', 'CONDITIONING', 'Caminhada carregando pesos, excelente para pegada, core e condicionamento.'),
        ('Sled Push', 'FUNCTIONAL_TRAINING', 'CONDITIONING', 'Empurrar trenó com carga, foco em potência de pernas e capacidade cardiovascular.'),
        ('Bear Crawl', 'FUNCTIONAL_TRAINING', 'MOBILITY', 'Deslocamento em quatro apoios, trabalhando coordenação, ombros e core.'),
        ('Jumping Jack', 'FUNCTIONAL_TRAINING', 'CONDITIONING', 'Movimento cíclico de braços e pernas para aquecimento e condicionamento.'),
        ('Air Squat', 'FUNCTIONAL_TRAINING', 'LEGS', 'Agachamento livre com peso corporal.'),
        ('Walking Lunge', 'FUNCTIONAL_TRAINING', 'LEGS', 'Avanço caminhando, trabalhando força unilateral e estabilidade.'),
        ('Bear Plank Shoulder Tap', 'FUNCTIONAL_TRAINING', 'CORE', 'Toques alternados nos ombros em posição de prancha, trabalhando estabilidade de core.'),
        ('Jump Squat', 'FUNCTIONAL_TRAINING', 'PLYOMETRICS', 'Agachamento com salto para potência de membros inferiores.'),
        ('Lateral Bound', 'FUNCTIONAL_TRAINING', 'PLYOMETRICS', 'Saltos laterais para potência, estabilidade e controle de aterrissagem.'),
        ('Circuito Funcional', 'FUNCTIONAL_TRAINING', 'CIRCUIT', 'Sequência de exercícios funcionais organizada em estações ou blocos de tempo.')
) AS v(name, sport_type, category, description)
WHERE NOT EXISTS (
    SELECT 1
    FROM techniques t
    WHERE LOWER(t.name) = LOWER(v.name)
      AND t.sport_type = v.sport_type
      AND t.created_by IS NULL
);

-- =========================================================
-- RUNNING
-- =========================================================

INSERT INTO techniques (id, name, sport_type, category, description, created_at, updated_at)
SELECT
    gen_random_uuid(),
    v.name,
    v.sport_type,
    v.category,
    v.description,
    NOW(),
    NOW()
FROM (
    VALUES
        ('Corrida Leve', 'RUNNING', 'EASY_RUN', 'Corrida em ritmo confortável, zona 1-2 de frequência cardíaca.'),
        ('Long Run', 'RUNNING', 'LONG_RUN', 'Corrida longa semanal para construção de base aeróbica.'),
        ('Intervalado', 'RUNNING', 'INTERVAL', 'Séries de alta intensidade alternadas com recuperação ativa.'),
        ('Tempo Run', 'RUNNING', 'TEMPO', 'Corrida em ritmo limiar anaeróbico por tempo ou distância definidos.'),
        ('Fartlek', 'RUNNING', 'INTERVAL', 'Variações livres de ritmo ao longo da corrida sem estrutura rígida.'),
        ('Corrida de Recuperação', 'RUNNING', 'EASY_RUN', 'Corrida muito leve para facilitar a recuperação entre sessões intensas.'),
        ('Strides', 'RUNNING', 'SPEED_WORK', 'Acelerações curtas de 80-100m para desenvolver eficiência de passada.'),
        ('Skipping', 'RUNNING', 'RUNNING_DRILL', 'Exercício de coordenação elevando joelhos para melhorar mecânica de corrida.'),
        ('Tiro de 400m', 'RUNNING', 'INTERVAL', 'Séries de 400 metros em ritmo forte com recuperação programada.'),
        ('Tiro de 1km', 'RUNNING', 'INTERVAL', 'Séries de 1 quilômetro para desenvolver resistência em ritmo intenso.'),
        ('Subida', 'RUNNING', 'SPEED_WORK', 'Treino em aclive para força específica, potência e resistência muscular.'),
        ('Progressivo', 'RUNNING', 'TEMPO', 'Corrida iniciando em ritmo leve e aumentando progressivamente até ritmo moderado ou forte.'),
        ('Educativo Anfersen', 'RUNNING', 'RUNNING_DRILL', 'Educativo levando calcanhares aos glúteos para coordenação e mecânica.'),
        ('Drill A-Skip', 'RUNNING', 'RUNNING_DRILL', 'Educativo de corrida com elevação de joelho e coordenação de braços.'),
        ('Corrida Regenerativa', 'RUNNING', 'EASY_RUN', 'Sessão leve para manter volume sem gerar grande fadiga.')
) AS v(name, sport_type, category, description)
WHERE NOT EXISTS (
    SELECT 1
    FROM techniques t
    WHERE LOWER(t.name) = LOWER(v.name)
      AND t.sport_type = v.sport_type
      AND t.created_by IS NULL
);