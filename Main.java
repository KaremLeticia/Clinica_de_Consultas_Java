import java.util.*;
import java.io.File;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.FileWriter;
import java.io.PrintWriter;

public class Main {
    public static void main(String[] args) throws IOException {

        Scanner scanner = new Scanner(System.in);
        Map<Long, Consulta> consultasPorId = new HashMap<>();
        SequencialHelper sequencialConsulta = new SequencialHelper(1L);

        Map<Long, Paciente> pacientesPorId = new HashMap<>();
        SequencialHelper sequencialPaciente = new SequencialHelper(1L);
        String nomeArquivoPacientes = "pacientes.txt";
        try {
            File arquivoPacientes = new File(nomeArquivoPacientes);
            arquivoPacientes.createNewFile();
            BufferedReader reader = new BufferedReader(new FileReader(nomeArquivoPacientes));
            String pacienteArquivo;
            while ((pacienteArquivo = reader.readLine()) != null) {
                String[] dadosPaciente = pacienteArquivo.split("\\|");
                pacientesPorId.put(sequencialPaciente.obtenhaSequencialNovaEntidade(), new Paciente(Long.getLong(dadosPaciente[0]), dadosPaciente[1], dadosPaciente[2]));
            }
            reader.close();
        } catch (IOException e) {
            System.out.println("Ocorreu um erro ao carregar os pacientes.");
            throw e;
        }

        boolean exibirMenu = true;
        while (exibirMenu) {
            System.out.println("\nMenu da Clínica:");
            System.out.println("1. Cadastrar paciente;");
            System.out.println("2. Marcar consulta;");
            System.out.println("3. Cancelar consulta;");
            System.out.println("4. Sair;");
            System.out.print("Escolha o número referente à opção desejada: ");
            int funcionalidade = scanner.nextInt();
            scanner.nextLine();

            switch (funcionalidade) {
                case 1:
                    System.out.println("Informe os dados do paciente abaixo: ");
                    System.out.print("Nome do paciente: ");
                    String nomePaciente = scanner.nextLine();
                    System.out.print("Telefone do paciente: ");
                    String telefonePaciente = scanner.nextLine();
                    Paciente novoPaciente = new Paciente(sequencialPaciente.obtenhaSequencialNovaEntidade(), nomePaciente, telefonePaciente);
                    pacientesPorId.put(novoPaciente.getId(), novoPaciente);
                    adicionePacienteNoArquivo(nomeArquivoPacientes, novoPaciente);
                    System.out.println("Paciente cadastrado com sucesso!");
                    break;
                case 2:
                    if (pacientesPorId.keySet().isEmpty()) {
                        System.out.println("Não há pacientes cadastrados para marcar uma consulta. Cadastre um paciente e tente novamente.");
                        break;
                    }

                    System.out.println("Lista de pacientes cadastrados:");
                    for (Long idPaciente : pacientesPorId.keySet()) {
                        System.out.println(idPaciente + ". " + pacientesPorId.get(idPaciente).getNome());
                    }
                    System.out.print("Escolha o número do paciente: ");
                    Long idPacienteSelecionado = scanner.nextLong();
                    scanner.nextLine();
                    if (pacientesPorId.get(idPacienteSelecionado) == null) {
                        System.out.println("Número do paciente não encontrado. Escolha apenas as opções referentes aos pacientes listados.");
                        break;
                    }

                    System.out.print("Data da consulta (dd/mm/aaaa): ");
                    String dataConsulta = scanner.nextLine();
                    System.out.print("Hora da consulta: ");
                    String horaConsulta = scanner.nextLine();
                    System.out.print("Especialidade desejada: ");
                    String especialidadeConsulta = scanner.nextLine();
                    Consulta novaConsulta = new Consulta(idPacienteSelecionado, dataConsulta, horaConsulta, especialidadeConsulta);
                    consultasPorId.put(sequencialConsulta.obtenhaSequencialNovaEntidade(), novaConsulta);
                    System.out.println("Consulta agendada com sucesso!");
                    break;
                case 3:
                    if (consultasPorId.keySet().isEmpty()) {
                        System.out.println("Não há consultas agendadas para serem removidas.");
                        break;
                    }

                    System.out.println("Lista de agendamentos existentes:");
                    for (Long idConsulta : consultasPorId.keySet()) {
                        Consulta consulta = consultasPorId.get(idConsulta);
                        Paciente pacienteConsulta = pacientesPorId.get(consulta.getIdPaciente());
                        System.out.println(idConsulta + ". " + pacienteConsulta.getNome() + " - " + consulta.getData() + " às " + consulta.getHora() + " (" + consulta.getEspecialidade() + ")");
                    }
                    System.out.print("Escolha o número da consulta a ser cancelada: ");
                    Long idConsulta = scanner.nextLong();
                    scanner.nextLine();
                    if (consultasPorId.get(idConsulta) == null) {
                        System.out.println("Número de consulta não encontrado. Escolha apenas as opções referentes às consultas listadas.");
                        break;
                    }
                    consultasPorId.remove(idConsulta);
                    System.out.println("Consulta cancelada com sucesso!");
                    break;
                case 4:
                    System.out.println("Encerrando o programa. Até mais!");
                    exibirMenu = false;
                    break;
                default:
                    System.out.println("Opção inválida. Tente novamente.");
            }
        }
    }

    private static void adicionePacienteNoArquivo(String nomeArquivoPacientes, Paciente novoPaciente) throws IOException {
        FileWriter fileWriter = new FileWriter(nomeArquivoPacientes, true);
        PrintWriter printWriter = new PrintWriter(fileWriter);
        printWriter.println(novoPaciente.getId() + "|" + novoPaciente.getNome() + "|" + novoPaciente.getTelefone() + "|");
        printWriter.close();
    }
}