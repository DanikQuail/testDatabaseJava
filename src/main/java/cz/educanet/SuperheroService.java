package cz.educanet;

import jakarta.enterprise.context.RequestScoped;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Named;

import java.sql.*;
import java.util.ArrayList;
import java.util.Map;

@Named
@RequestScoped
public class SuperheroService {
    public ArrayList<Publisher> getPublishers() {
        try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost/superhero?user=root&password=");
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(
                     "SELECT pub.id, pub.publisher_name, COUNT(pub.id) FROM publisher AS PUB\n" +
                             "JOIN superhero s on PUB.id = s.publisher_id\n" +
                             "GROUP BY pub.id")) {
            ArrayList<Publisher> publishers = new ArrayList<>();
            while (resultSet.next()) {
                String pubName = resultSet.getString(2);
                if (resultSet.getString(2).isEmpty()) {
                    pubName = "N/A";
                }

                publishers.add(new Publisher(resultSet.getInt(1), pubName, resultSet.getInt(3)));
            }
            return publishers;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public ArrayList<Superhero> getSuperhero() {
        Map<String,String> params =
                FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
        String action = params.get("change");
        try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost/superhero?user=root&password=");
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(
                     "SELECT sh.superhero_name, sh.full_name, g.gender, r.race, a.alignment FROM superhero AS sh\n" +
                             "                             JOIN gender AS g ON (sh.gender_id = g.id)\n" +
                             "                             JOIN race AS r ON (sh.race_id = r.id)\n" +
                             "                             JOIN alignment AS a ON (sh.alignment_id = a.id)\n" +
                             "                             JOIN publisher AS pub ON (sh.publisher_id = pub.id)\n" +
                             "")) {
            ArrayList<Superhero> superheroes = new ArrayList<>();
            /*
             PreparedStatement preparedStatement = connection.prepareStatement(
                     "SELECT sh.superhero_name, sh.full_name, g.gender, r.race, a.alignment FROM superhero AS sh\n" +
                             "                             JOIN gender AS g ON (sh.gender_id = g.id)\n" +
                             "                             JOIN race AS r ON (sh.race_id = r.id)\n" +
                             "                             JOIN alignment AS a ON (sh.alignment_id = a.id)\n" +
                             "                             JOIN publisher AS pub ON (sh.publisher_id = pub.id)\n" +
                             "WHERE (sh.publisher_id = ?)"
             );
             ResultSet resultSet = preparedStatement.executeQuery()) {

            preparedStatement.setString(1, map.get("change"));
             */
            while (resultSet.next()) {
                superheroes.add(new Superhero(
                        resultSet.getString(1),
                        resultSet.getString(2),
                        resultSet.getString(3),
                        resultSet.getString(4),
                        resultSet.getString(5)));
            }
            return superheroes;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }



}
