package ir.maktab.Q1;

import ir.maktab.Q1.mockdata.MockData;
import ir.maktab.Q1.model.Person;
import ir.maktab.Q1.model.PersonSummary;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

public class Main {
    public static void main(String[] args) {
        List<Person> people = MockData.getPeople();

        //1
        List<Person> under50people = filterPeopleAbove50(people);
        for (Person person : under50people) {
            System.out.println(person);
        }

        //2
        List<Person> sortedByUserName = sortPeopleByUsername(people);
        for (Person person : sortedByUserName) {
            System.out.println(person);
        }

        //3
        List<Person> sortedByAgeAndLName = sortPeopleByAgeAndLastName(people);
        for (Person person : sortedByAgeAndLName) {
            System.out.println(person);
        }

        //4
        Set<String> ipv4List = extractIPv4Addresses(people);
        for (String ip : ipv4List) {
            System.out.println(ip);
        }

        //5
        Map<String, Person> map = filterAndMapPeople(people);
        for (Map.Entry<String, Person> entry : map.entrySet()) {
            System.out.println(entry.getKey() + "   " + entry.getValue());
        }

        //6
        OptionalDouble average = correctDateOfBirthAndFilterMales(people);
        System.out.println(average);
    }

    public static List<Person> filterPeopleAbove50(List<Person> people) {
        return people.stream()
                .filter(person -> person.getAge() < 50)
                .collect(Collectors.toList());
    }

    public static List<Person> sortPeopleByUsername(List<Person> people) {
        return people.stream()
                .sorted(Comparator.comparing(Person::getUsername)).toList();
    }

    public static List<Person> sortPeopleByAgeAndLastName(List<Person> people) {
        return people.stream()
                .sorted(Comparator.comparingInt(Person::getAge))
                .sorted(Comparator.comparing(Person::getUsername)).toList();
    }

    public static Set<String> extractIPv4Addresses(List<Person> people) {
        return people.stream()
                .map(Person::getIpv4)
                .collect(Collectors.toSet());
    }

    public static Map<String, Person> filterAndMapPeople(List<Person> people) {
        return people.stream()
                .sorted(Comparator.comparing(Person::getLastName))
                .filter(person -> person.getGender().equalsIgnoreCase("Female"))
                .dropWhile(person -> person.getFirstName().startsWith("A"))
                .skip(5)
                .limit(100)
                .collect(Collectors.toMap(
                        person -> person.getFirstName() + " " + person.getLastName(), Function.identity(),
                        (first, second) -> first
                ));

    }

    public static OptionalDouble correctDateOfBirthAndFilterMales(List<Person> persons) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        return persons.stream()
                .filter(person -> person.getGender().equals("Male"))
                .map(person -> {
                    try {
                        return new PersonSummary(person.getId(), person.getFirstName(), person.getLastName(), 2023 - (dateFormat.parse(person.getBirthDate()).getYear() + 1900), dateFormat.parse(person.getBirthDate()));
                    } catch (ParseException e) {
                        throw new RuntimeException(e);
                    }
                })
                .map(PersonSummary::getAge)
                .mapToDouble(Integer::doubleValue).average();
    }
}