# Welcome to the card-collection-app wiki!

### Introduction
This app is part of the Software Development tools Module ran by _Siobhan Drohan_.

In this module we are tasked to work on an app of our choice using the github workflow just like it is used in the real world.

1. [What is cards-collection-app?](#1)
2. [How does it work?](#2)
3. [What can it do?](#3)

***

<h3 name="1">What is cards-collection-app?</h3>
The cards-collection-app is an app that allows users to track their collection. Mainly focusing on collectible cards. Since the Covid-19 pandemic, a lot of people started to pickup hobbies such as collecting cards. This has made the collectible market blow up.

Collectible cards were always sold at small shops like Centra, but now they can be found in a number of places such as:
* book stores
* comic shops
* GameStop
* toy shops such as Smyths

<img src="https://news.artnet.com/app/news-upload/2021/01/blastoise.jpeg" alt="image of Pokémon card" width="20%;"/>

This explosion of people getting into card collecting has made the market for cards go crazy. Now you can find collectible cards for many things such as:
* barbie cards
* tractor cards
* racing cards

Where collectible cards were mainly focused on sports and Japanese culture aka anime based cards such as Pokémon or Yu-Gi-Oh!.

***

<h3 name="2">How does it work?</h3>
The app is a very simple CRUD console app.

- The app displays a menu to the user with instructions.
- The user then enters the corresponding number to the function that they want to use.
- The app then executes the chosen function.

This is what the menu looks like:

                  ---------------------------------------
                  |        CARD COLLECTION APP          |
                  ---------------------------------------
                  | MENU                                |
                  |   1) Add Card                       |
                  |   2) Update Card                    |
                  |   3) Delete Card                    |
                  |   4) List Cards                     |
                  ---------------------------------------
                  |   5) Search by Name                 |
                  ---------------------------------------
                  |   0) Exit                           |
                  ---------------------------------------

The menu is displayed in the main function by running the runMenu() function.

`fun main(args: Array<String>) {
    load()
    runMenu()
}`

***

<h3 name="3">What can it do?</h3>
So far the app has a couple of functions that the user can use.

0. Exit
1. Add cards
2. Update cards
3. Delete cards
4. Display all cards
5. Search by Name

The app has persistence implemented into it therefore it can save and load the users cards collection. This is especially handy as we want to keep track of the cards the user has collected and allow them to execute functions on those cards.

This app has a basic card object `card1 = Card(name,cardNum,rarity,quality)` which then is added to an `ArrayList<Card>()` that then is stored and loaded from an xml file.
