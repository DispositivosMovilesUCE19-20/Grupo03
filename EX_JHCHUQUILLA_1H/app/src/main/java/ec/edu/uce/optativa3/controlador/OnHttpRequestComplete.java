package ec.edu.uce.optativa3.controlador;
public interface OnHttpRequestComplete {

        /**
         * Este método es ejecutado al terminar la ejecucion del método excecute del httpclient
         * devuelve un Response, con los datos de la solicitud
         * @param status Instancia de la clase Response
         */
        public void onComplete(Response status);

}
