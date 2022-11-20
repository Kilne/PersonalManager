package org.project;


import org.project.database.MyPostgreWrapper;

public class Main {
    public static void main(String[] args) {

        MyPostgreWrapper postgreWrapper = new MyPostgreWrapper("jdbc:postgresql://localhost:5432/postgres?user=postgres&password=admin");
        postgreWrapper.connect();
        System.out.println(postgreWrapper.select("SELECT * FROM public.\"Test\""));
        System.out.println(postgreWrapper.insert("INSERT INTO public.\"Test\" VALUES ('test 4')"));
        System.out.println(postgreWrapper.update("UPDATE public.\"Test\" SET \"Data\" = 'test 5' WHERE \"Data\" = 'test 4'"));
        System.out.println(postgreWrapper.delete("DELETE FROM public.\"Test\" WHERE \"Data\" = 'test 5'"));
        postgreWrapper.disconnect();
    }
}