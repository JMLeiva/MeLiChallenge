# MeLiChallenge

This is a demo app created by Juan Martin Leiva as a test for Mercado Libre (a.k.a. MeLi)The app allows the user to perform a search in Mercado Libre, then you can see it\'s details.
	 Inside the details, you can see all the item pictures, and perform a zoom in them.
         It also saves in disk your last searchs (the app comes with a simple preloaded data set), and shows as hint when typing in the search view.
         The following Android componentes were used:   
           * Lifecycle   
           * Room to persist data
        The following 3ยบ party libraries were used:
           * Glide to handle picture loading and caching
           * Retrofit for the rest client
           * Gson to parse Json
           * okhttp3 to process http request (using Retrofit)
           * Butterknife for view binding
           * PagedRecyclerView (a work of my own) to display the paged lists
           * Dagger2 for dependency injection
           * BigImageViewer to perform pan and zoom in pictures
           
#################################################################################################################################           
           
Esta es una app demo creada por Juan Martin Leiva como desafío para Mercado Libre (a.k.a. MeLi)La app permite al usuario realizar búsquedas en Mercado Libre (a través de su API pública), luego puede sus detalles.
	 En los detalles, puedes ver todas las imagenes del producto y hacerles zoom.
         Tambien guarda en disco las búsquedas realziadas (la app viene con una lista de búsquedas pre-cargadas), y las muestra como sugerencias al realizar una búsqueda.
         Los siguientes compoenentes de Android fueron utilziados:   
         * Lifecycle    
         * Room para persistir data en disco
        Las siguientes librerías de terceros fueron utilizadas:
           * Glide para el manejo de carga y caching de imágenes
           * Retrofit para el cliente REST
           * Gson para parsear Json
           * okhttp3 para procesar los HTTP requests (junto con Retrofit)
           * Butterknife para el View Binding
           * PagedRecyclerView (una libreria de autoria) para manajar listas paginadas
           * Dagger2 para inyección de dependencia
           * BigImageViewer para permitir el zoom y "panneo" de imágenes
