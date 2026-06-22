-- Global techniques (created_by IS NULL = cannot be modified or deleted by users)

-- JIU_JITSU
INSERT INTO techniques (id, name, sport_type, category, description, created_at, updated_at) VALUES
    (gen_random_uuid(), 'Armbar',              'JIU_JITSU', 'SUBMISSION',  'Chave de braço aplicada estendendo o cotovelo do oponente.',                            NOW(), NOW()),
    (gen_random_uuid(), 'Triângulo',           'JIU_JITSU', 'SUBMISSION',  'Finalização com as pernas envolvendo o pescoço e um braço do oponente.',                NOW(), NOW()),
    (gen_random_uuid(), 'Kimura',              'JIU_JITSU', 'SUBMISSION',  'Chave de ombro aplicada com controle de dois pontos no braço.',                         NOW(), NOW()),
    (gen_random_uuid(), 'Rear Naked Choke',    'JIU_JITSU', 'SUBMISSION',  'Estrangulamento pelas costas com o antebraço pressionando a carótida.',                 NOW(), NOW()),
    (gen_random_uuid(), 'Ezekiel Choke',       'JIU_JITSU', 'SUBMISSION',  'Estrangulamento aplicado com a manga do kimono ou punho.',                              NOW(), NOW()),
    (gen_random_uuid(), 'Tomoe Nage',          'JIU_JITSU', 'TAKEDOWN',   'Projeção sacrifício usando os pés no quadril do oponente.',                              NOW(), NOW()),
    (gen_random_uuid(), 'Double Leg',          'JIU_JITSU', 'TAKEDOWN',   'Queda dupla agarrando as duas pernas do oponente.',                                      NOW(), NOW()),
    (gen_random_uuid(), 'Guard Pull',          'JIU_JITSU', 'GUARD',      'Técnica de puxar o oponente para a guarda.',                                             NOW(), NOW()),
    (gen_random_uuid(), 'Closed Guard',        'JIU_JITSU', 'GUARD',      'Guarda fechada com os calcanhares cruzados nas costas do oponente.',                     NOW(), NOW()),
    (gen_random_uuid(), 'X-Guard',             'JIU_JITSU', 'GUARD',      'Guarda em X com controle das pernas do oponente de baixo.',                              NOW(), NOW()),
    (gen_random_uuid(), 'Spider Guard',        'JIU_JITSU', 'GUARD',      'Guarda com os pés nos bíceps do oponente e controle dos punhos.',                        NOW(), NOW()),
    (gen_random_uuid(), 'Scissor Sweep',       'JIU_JITSU', 'SWEEP',      'Raspagem em tesoura executada da guarda fechada.',                                       NOW(), NOW()),
    (gen_random_uuid(), 'Hip Bump Sweep',      'JIU_JITSU', 'SWEEP',      'Raspagem projetando o quadril para frente da guarda fechada.',                           NOW(), NOW()),
    (gen_random_uuid(), 'Torreando',           'JIU_JITSU', 'PASS',       'Passagem de guarda circulando pelas pernas sem agarrá-las.',                             NOW(), NOW()),
    (gen_random_uuid(), 'Knee Slice Pass',     'JIU_JITSU', 'PASS',       'Passagem de guarda cortando com o joelho entre as pernas do oponente.',                  NOW(), NOW());

-- STRENGTH_TRAINING
INSERT INTO techniques (id, name, sport_type, category, description, created_at, updated_at) VALUES
    (gen_random_uuid(), 'Supino Reto',         'STRENGTH_TRAINING', 'PUSH',      'Exercício de empurrar com a barra deitado no banco plano, foco no peitoral.',     NOW(), NOW()),
    (gen_random_uuid(), 'Agachamento',         'STRENGTH_TRAINING', 'LEGS',      'Exercício fundamental de membros inferiores com barra nas costas.',                NOW(), NOW()),
    (gen_random_uuid(), 'Levantamento Terra',  'STRENGTH_TRAINING', 'FULL_BODY', 'Movimento composto puxando a barra do chão até a extensão total do quadril.',     NOW(), NOW()),
    (gen_random_uuid(), 'Desenvolvimento',     'STRENGTH_TRAINING', 'PUSH',      'Exercício de empurrar o peso acima da cabeça, trabalha deltoides.',                NOW(), NOW()),
    (gen_random_uuid(), 'Remada Curvada',      'STRENGTH_TRAINING', 'PULL',      'Puxada com tronco inclinado, foco nos dorsais e romboides.',                       NOW(), NOW()),
    (gen_random_uuid(), 'Pull-up',             'STRENGTH_TRAINING', 'PULL',      'Barra fixa com pegada pronada, foco em dorsais e bíceps.',                         NOW(), NOW()),
    (gen_random_uuid(), 'Rosca Direta',        'STRENGTH_TRAINING', 'PULL',      'Flexão de cotovelo com halteres ou barra para trabalho de bíceps.',                NOW(), NOW()),
    (gen_random_uuid(), 'Tríceps Testa',       'STRENGTH_TRAINING', 'PUSH',      'Extensão de cotovelo com barra deitado, foco no tríceps.',                         NOW(), NOW()),
    (gen_random_uuid(), 'Leg Press',           'STRENGTH_TRAINING', 'LEGS',      'Extensão de membros inferiores no aparelho, menor carga axial que o agachamento.', NOW(), NOW()),
    (gen_random_uuid(), 'Cadeira Extensora',   'STRENGTH_TRAINING', 'LEGS',      'Isolamento do quadríceps no aparelho de extensão.',                                NOW(), NOW()),
    (gen_random_uuid(), 'Mesa Flexora',        'STRENGTH_TRAINING', 'LEGS',      'Isolamento dos isquiotibiais no aparelho de flexão.',                              NOW(), NOW()),
    (gen_random_uuid(), 'Supino Inclinado',    'STRENGTH_TRAINING', 'PUSH',      'Variação do supino com banco inclinado, enfatiza a porção clavicular do peitoral.', NOW(), NOW()),
    (gen_random_uuid(), 'Crucifixo',           'STRENGTH_TRAINING', 'PUSH',      'Exercício de isolamento para peitoral com halteres em adução.',                    NOW(), NOW()),
    (gen_random_uuid(), 'Barra Fixa Supinada', 'STRENGTH_TRAINING', 'PULL',      'Pull-up com pegada supinada (chin-up), maior ativação do bíceps.',                 NOW(), NOW()),
    (gen_random_uuid(), 'Stiff',               'STRENGTH_TRAINING', 'LEGS',      'Levantamento terra romeno, foco em isquiotibiais e glúteos.',                      NOW(), NOW());

-- FUNCTIONAL_TRAINING
INSERT INTO techniques (id, name, sport_type, category, description, created_at, updated_at) VALUES
    (gen_random_uuid(), 'Burpee',              'FUNCTIONAL_TRAINING', 'HIIT',        'Movimento completo: agachamento, prancha, flexão e salto.',                    NOW(), NOW()),
    (gen_random_uuid(), 'Box Jump',            'FUNCTIONAL_TRAINING', 'PLYOMETRICS', 'Salto em caixa desenvolvendo potência de membros inferiores.',                 NOW(), NOW()),
    (gen_random_uuid(), 'Kettlebell Swing',    'FUNCTIONAL_TRAINING', 'FULL_BODY',   'Balanço do kettlebell com extensão explosiva do quadril.',                     NOW(), NOW()),
    (gen_random_uuid(), 'Turkish Get-Up',      'FUNCTIONAL_TRAINING', 'BALANCE',     'Movimento complexo do solo até em pé com carga acima da cabeça.',              NOW(), NOW()),
    (gen_random_uuid(), 'Prancha',             'FUNCTIONAL_TRAINING', 'CORE',        'Isometria de core em posição de prancha frontal.',                             NOW(), NOW()),
    (gen_random_uuid(), 'Mountain Climber',    'FUNCTIONAL_TRAINING', 'CONDITIONING','Simulação de escalada no chão com alternância rápida de joelhos.',              NOW(), NOW()),
    (gen_random_uuid(), 'Thruster',            'FUNCTIONAL_TRAINING', 'FULL_BODY',   'Combinação de agachamento frontal com desenvolvimento.',                        NOW(), NOW()),
    (gen_random_uuid(), 'Battle Rope',         'FUNCTIONAL_TRAINING', 'CONDITIONING','Ondulações com cordas pesadas para condicionamento metabólico.',                NOW(), NOW());

-- RUNNING
INSERT INTO techniques (id, name, sport_type, category, description, created_at, updated_at) VALUES
    (gen_random_uuid(), 'Corrida Leve',        'RUNNING', 'EASY_RUN',     'Corrida em ritmo confortável, zona 1-2 de frequência cardíaca.',                        NOW(), NOW()),
    (gen_random_uuid(), 'Long Run',            'RUNNING', 'LONG_RUN',     'Corrida longa semanal para construção de base aeróbica.',                               NOW(), NOW()),
    (gen_random_uuid(), 'Intervalado',         'RUNNING', 'INTERVAL',     'Séries de alta intensidade alternadas com recuperação ativa.',                          NOW(), NOW()),
    (gen_random_uuid(), 'Tempo Run',           'RUNNING', 'TEMPO',        'Corrida em ritmo limiar anaeróbico por tempo ou distância definidos.',                  NOW(), NOW()),
    (gen_random_uuid(), 'Fartlek',             'RUNNING', 'INTERVAL',     'Variações livres de ritmo ao longo da corrida sem estrutura rígida.',                   NOW(), NOW()),
    (gen_random_uuid(), 'Corrida de Recuperação', 'RUNNING', 'EASY_RUN',  'Corrida muito leve para facilitar a recuperação entre sessões intensas.',               NOW(), NOW()),
    (gen_random_uuid(), 'Strides',             'RUNNING', 'SPEED_WORK',   'Acelerações curtas de 80-100m para desenvolver eficiência de passada.',                 NOW(), NOW()),
    (gen_random_uuid(), 'Skipping',            'RUNNING', 'RUNNING_DRILL','Exercício de coordenação elevando joelhos para melhorar mecânica de corrida.',          NOW(), NOW());
