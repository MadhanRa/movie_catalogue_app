package id.madhanra.submission.utils

import id.madhanra.submission.R
import id.madhanra.submission.data.DataEntity

object DataSource {

    fun generateMovie(): List<DataEntity> {
        val movies = ArrayList<DataEntity>()

        movies.add(DataEntity(
            "m1",
            "Alita: Battle Angel",
            "When Alita awakens with no memory of who she is in a future world she does not recognize, she is taken in by Ido, a compassionate doctor who realizes that somewhere in this abandoned cyborg shell is the heart and soul of a young woman with an extraordinary past.",
            "(2019)",
            "2h 2m",
            "Action, Science Fiction, Adventure",
            "An angel falls. A warrior rises.",
            "PG-13",
            false,
            "71",
            "https://image.tmdb.org/t/p/w600_and_h900_bestv2/9ymtcxcP5T8nruAtpy6f8mHIcbH.jpg"
        ))
        movies.add(DataEntity(
            "m2",
            "Aquaman",
            "Once home to the most advanced civilization on Earth, Atlantis is now an underwater kingdom ruled by the power-hungry King Orm. With a vast army at his disposal, Orm plans to conquer the remaining oceanic people and then the surface world. Standing in his way is Arthur Curry, Orm's half-human, half-Atlantean brother and true heir to the throne.",
            "(2018)",
            "2h 24m",
            "Action, Adventure, Fantasy",
            "Home Is Calling.",
            "PG-13",
            false,
            "69",
            "https://image.tmdb.org/t/p/w600_and_h900_bestv2/c3jIPDYNRHMJ51CMdZpPAEFb0JP.jpg"
        ))
        movies.add(DataEntity(
            "m3",
            "Fantastic Beasts: The Crimes of Grindelwald",
            "Gellert Grindelwald has escaped imprisonment and has begun gathering followers to his cause—elevating wizards above all non-magical beings. The only one capable of putting a stop to him is the wizard he once called his closest friend, Albus Dumbledore. However, Dumbledore will need to seek help from the wizard who had thwarted Grindelwald once before, his former student Newt Scamander, who agrees to help, unaware of the dangers that lie ahead. Lines are drawn as love and loyalty are tested, even among the truest friends and family, in an increasingly divided wizarding world.",
            "(2018)",
            "2h 14m",
            "Adventure, Fantasy, Drama",
            "Fate of One, Future of All.",
            "PG-13",
            false,
            "69",
            "https://image.tmdb.org/t/p/w600_and_h900_bestv2/xijolYnj6aduYnAOGhyVCu5P3yq.jpg"
        ))
        movies.add(DataEntity(
            "m4",
            "Avengers: Infinity War",
            "As the Avengers and their allies have continued to protect the world from threats too large for any one hero to handle, a new danger has emerged from the cosmic shadows: Thanos. A despot of intergalactic infamy, his goal is to collect all six Infinity Stones, artifacts of unimaginable power, and use them to inflict his twisted will on all of reality. Everything the Avengers have fought for has led up to this moment - the fate of Earth and existence itself has never been more uncertain.",
            "(2018)",
            "2h 29m",
            "Adventure, Action, Science Fiction",
            "An entire universe. Once and for all",
            "PG-13",
            false,
            "83",
           "https://image.tmdb.org/t/p/w600_and_h900_bestv2/bxfCEo5a6t6gxUR9EjJLlqCe73i.jpg"
        ))
        movies.add(DataEntity(
            "m5",
            "Mary Queen of Scots",
            "In 1561, Mary Stuart, widow of the King of France, returns to Scotland, reclaims her rightful throne and menaces the future of Queen Elizabeth I as ruler of England, because she has a legitimate claim to the English throne. Betrayals, rebellions, conspiracies and their own life choices imperil both Queens. They experience the bitter cost of power, until their tragic fate is finally fulfilled.",
            "(2018)",
            "2h 4m",
            "Drama, History",
            "Bow to No One",
            "R",
            false,
            "66",
            "https://image.tmdb.org/t/p/w600_and_h900_bestv2/mYrzp2ezSrgVFsa3aHxqqbFHpxx.jpg"
        ))
        movies.add(DataEntity(
            "m6",
            "The SpongeBob Movie: Sponge on the Run",
            "When his best friend Gary is suddenly snatched away, SpongeBob takes Patrick on a madcap mission far beyond Bikini Bottom to save their pink-shelled pal.",
            "(2020)",
            "1h 35m",
            "Fantasy, Animation, Adventure, Comedy, Family",
            "They're Not in Bikini Bottom Anymore.",
            "L",
            false,
            "82",
            "https://image.tmdb.org/t/p/w600_and_h900_bestv2/jcJpkQWRU8gsZlo830eQ8ryXKNU.jpg"
        ))
        movies.add(DataEntity(
            "m7",
            "2067",
            "A lowly utility worker is called to the future by a mysterious radio signal, he must leave his dying wife to embark on a journey that will force him to face his deepest fears in an attempt to change the fabric of reality and save humankind from its greatest environmental crisis yet.",
            "(2020)",
            "1h 54m",
            "Science Fiction, Thriller, Drama",
            "The fight for the future has begun.",
            "16+",
            false,
            "48",
            "https://image.tmdb.org/t/p/w600_and_h900_bestv2/7D430eqZj8y3oVkLFfsWXGRcpEG.jpg"
        ))
        movies.add(DataEntity(
            "m8",
            "Demon Slayer: Kimetsu no Yaiba - The Movie: Mugen Train",
            "Tanjirō Kamado, joined with Inosuke Hashibira, a boy raised by boars who wears a boar's head, and Zenitsu Agatsuma, a scared boy who reveals his true power when he sleeps, boards the Infinity Train on a new mission with the Fire Hashira, Kyōjurō Rengoku, to defeat a demon who has been tormenting the people and killing the demon slayers who oppose it!",
            "(2020)",
            "1h 57m",
            "Animation, Action, History, Adventure, Fantasy, Drama",
            "With your blade, bring an end to the nightmare.",
            "PG12",
            false,
            "62",
            "https://image.tmdb.org/t/p/w600_and_h900_bestv2/h8Rb9gBr48ODIwYUttZNYeMWeUU.jpg"
        ))
        movies.add(DataEntity(
            "m9",
            "We Bare Bears: The Movie",
            "When Grizz, Panda, and Ice Bear's love of food trucks and viral videos get out of hand, the brothers are chased away from their home and embark on a trip to Canada, where they can live in peace.",
            "(2020)",
            "1h 9m",
            "Family, Animation, Adventure, Comedy",
            "",
            "G",
            false,
            "77",
            "https://image.tmdb.org/t/p/w600_and_h900_bestv2/kPzcvxBwt7kEISB9O4jJEuBn72t.jpg"
        ))
        movies.add(DataEntity(
            "m10",
            "Roald Dahl's The Witches",
            "In late 1967, a young orphaned boy goes to live with his loving grandma in the rural Alabama town of Demopolis. As the boy and his grandmother encounter some deceptively glamorous but thoroughly diabolical witches, she wisely whisks him away to a seaside resort. Regrettably, they arrive at precisely the same time that the world's Grand High Witch has gathered.",
            "(2020)",
            "1h 46m",
            "Fantasy, Family, Adventure, Comedy, Horror\n",
            "They're real!",
            "PG",
            false,
            "70",
            "https://image.tmdb.org/t/p/w600_and_h900_bestv2/b1C0FuXp4wiPmHLVKq4kwtDMgK6.jpg"
        ))
        return movies
    }

    fun generateTvShow(): List<DataEntity> {
        val tvShow = ArrayList<DataEntity>()

        tvShow.add(DataEntity(
            "tv1",
            "Doom Patrol",
            "The Doom Patrol’s members each suffered horrible accidents that gave them superhuman abilities — but also left them scarred and disfigured. Traumatized and downtrodden, the team found purpose through The Chief, who brought them together to investigate the weirdest phenomena in existence — and to protect Earth from what they find.",
            "(2019)",
            "49m",
            "Sci-Fi & Fantasy, Action & Adventure",
            "",
            "TV-MA",
            false,
            "76",
            "https://image.tmdb.org/t/p/w600_and_h900_bestv2/drlfSCIlMKrEeMPhi8pqY4xGxj.jpg"
        ))
        tvShow.add(DataEntity(
            "tv2",
            "Hanna",
            "This thriller and coming-of-age drama follows the journey of an extraordinary young girl as she evades the relentless pursuit of an off-book CIA agent and tries to unearth the truth behind who she is. Based on the 2011 Joe Wright film.",
            "(2019)",
            "50m",
            "Action & Adventure, Drama",
            "",
            "TV-MA",
            false,
            "74",
            "https://image.tmdb.org/t/p/w600_and_h900_bestv2/5nSSkcM3TgpllZ7yTyBOQEgAX36.jpg"
        ))
        tvShow.add(DataEntity(
            "tv3",
            "Riverdale",
            "Set in the present, the series offers a bold, subversive take on Archie, Betty, Veronica and their friends, exploring the surreality of small-town life, the darkness and weirdness bubbling beneath Riverdale’s wholesome facade.",
            "(2017)",
            "45m",
            "Drama, Mystery",
            "Small town. Big secrets.",
            "TV-14",
            false,
            "86",
           "https://image.tmdb.org/t/p/w600_and_h900_bestv2/6zBWSuYW3Ps1nTfeMS8siS4KUaA.jpg"
        ))
        tvShow.add(DataEntity(
            "tv4",
            "The Umbrella Academy",
            "A dysfunctional family of superheroes comes together to solve the mystery of their father's death, the threat of the apocalypse and more.",
            "(2019)",
            "55m",
            "Action & Adventure, Sci-Fi & Fantasy, Comedy, Drama",
            "Super. Dysfunctional. Family.",
            "TV-MA",
            false,
            "87",
            "https://image.tmdb.org/t/p/w600_and_h900_bestv2/scZlQQYnDVlnpxFTxaIv2g0BWnL.jpg"
        ))
        tvShow.add(DataEntity(
            "tv5",
            "The Walking Dead",
            "Sheriff's deputy Rick Grimes awakens from a coma to find a post-apocalyptic world dominated by flesh-eating zombies. He sets out to find his family and encounters many other survivors along the way.",
            "(2010)",
            "42m",
            "Action & Adventure, Drama, Sci-Fi & Fantasy",
            "Fight the dead. Fear the living.",
            "TV-MA",
            false,
            "79",
            "https://image.tmdb.org/t/p/w600_and_h900_bestv2/sPRpCetwIBVXqkR5sIjSG9pOJ5s.jpg"
        ))
        tvShow.add(DataEntity(
            "tv6",
            "Dark",
            "A missing child causes four families to help each other for answers. What they could not imagine is that this mystery would be connected to innumerable other secrets of the small town.",
            "(2017)",
            "53m",
            "Sci-Fi & Fantasy, Drama, Mystery, Crime",
            "Everything is connected",
            "16",
            false,
            "84",
            "https://image.tmdb.org/t/p/w600_and_h900_bestv2/nlTUgbZY1E4Dr5B25zLLkudIaV.jpg"
        ))
        tvShow.add(DataEntity(
            "tv7",
            "The Mandalorian",
            "After the fall of the Galactic Empire, lawlessness has spread throughout the galaxy. A lone gunfighter makes his way through the outer reaches, earning his keep as a bounty hunter.",
            "(2019)",
            "35m",
            "Sci-Fi & Fantasy, Action & Adventure, Western",
            "Bounty hunting is a complicated profession.",
            "TV-14",
            false,
            "85",
            "https://image.tmdb.org/t/p/w600_and_h900_bestv2/sWgBv7LV2PRoQgkxwlibdGXKz1S.jpg"
        ))
        tvShow.add(DataEntity(
            "tv8",
            "The Good Doctor",
            "A young surgeon with Savant syndrome is recruited into the surgical unit of a prestigious hospital. The question will arise: can a person who doesn't have the ability to relate to people actually save their lives?",
            "(2017)",
            "42m",
            "Drama",
            "His mind is a mystery, his methods are a miracle.",
            "TV-14",
            false,
            "86",
            "https://image.tmdb.org/t/p/w600_and_h900_bestv2/vRiYxmZQbI8zMnQNNgIb26jPGsf.jpg"
        ))
        tvShow.add(DataEntity(
            "tv9",
            "The Queen's Gambit",
            "In a Kentucky orphanage in the 1950s, a young girl discovers an astonishing talent for chess while struggling with addiction.",
            "(2020)",
            "1h",
            "Drama",
            "",
            "18+",
            false,
            "89",
            "https://image.tmdb.org/t/p/w600_and_h900_bestv2/zU0htwkhNvBQdVSIKB9s6hgVeFK.jpg"
        ))
        tvShow.add(DataEntity(
            "tv10",
            "Money Heist",
            "To carry out the biggest heist in history, a mysterious man called The Professor recruits a band of eight robbers who have a single characteristic: none of them has anything to lose. Five months of seclusion - memorizing every step, every detail, every probability - culminate in eleven days locked up in the National Coinage and Stamp Factory of Spain, surrounded by police forces and with dozens of hostages in their power, to find out whether their suicide wager will lead to everything or nothing.",
            "(2017)",
            "1h 10m",
            "Crime, Drama",
            "The perfect robbery.",
            "18",
            false,
            "83",
            "https://image.tmdb.org/t/p/w600_and_h900_bestv2/MoEKaPFHABtA1xKoOteirGaHl1.jpg"
        ))
        return tvShow
    }
}