public class SequencialHelper {

    private Long sequencial;

    public SequencialHelper(Long sequencial) {
        this.sequencial = sequencial;
    }

    public Long obtenhaSequencialNovaEntidade() {
        return sequencial++;
    }

}
