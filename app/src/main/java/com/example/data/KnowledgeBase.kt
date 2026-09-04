package com.example.data

object KnowledgeBase {

    val constitutionalArticles = listOf(
        ConstitutionalArticle(
            articleNumber = "Article 14",
            title = "Equality Before Law & Equal Protection",
            corePrinciple = "The State shall not deny to any person equality before the law or equal protection of the laws within the territory of India.",
            parliamentaryApplication = "Cite when attacking arbitrary executive discretion, discriminatory classification, or retrospective penalties. Remember the 'manifest arbitrariness' test laid down in Shayara Bano.",
            typicalMisuseOrControversy = "Do not confuse reasonable classification (intelligible differentia with rational nexus) with blanket equality."
        ),
        ConstitutionalArticle(
            articleNumber = "Article 19(1)(a) & 19(2)",
            title = "Freedom of Speech & Reasonable Restrictions",
            corePrinciple = "Guarantees freedom of speech and expression, subject only to reasonable restrictions under 19(2) like public order, sovereignty, security of State, contempt of court, incitement to an offence.",
            parliamentaryApplication = "Use to counter internet bans, media censorship, or speech-related arrests. Emphasize that speech cannot be curtailed on grounds outside Article 19(2) (Shreya Singhal v. UOI).",
            typicalMisuseOrControversy = "Opposition delegates often forget 19(2) restrictions, while ruling delegates treat 19(2) as an absolute bypass."
        ),
        ConstitutionalArticle(
            articleNumber = "Article 21",
            title = "Protection of Life & Personal Liberty",
            corePrinciple = "No person shall be deprived of his life or personal liberty except according to procedure established by law (expanded to 'just, fair, and reasonable' procedure in Maneka Gandhi).",
            parliamentaryApplication = "The bedrock against unlawful detentions, surveillance without statutory backing (Puttaswamy), and custodial excesses. Argue 'substantive due process' in all criminal law debates.",
            typicalMisuseOrControversy = "Article 21 cannot be suspended even during a declared National Emergency under Article 359 (post-44th Amendment)."
        ),
        ConstitutionalArticle(
            articleNumber = "Article 32 & 226",
            title = "Constitutional Remedies & Writ Jurisdiction",
            corePrinciple = "Article 32 is the 'heart and soul' of the Constitution granting direct access to the Supreme Court for Fundamental Rights enforcement. Article 226 gives wider writ powers to High Courts for any legal right.",
            parliamentaryApplication = "Deploy when defending judicial review against legislative overreach. High Court jurisdiction under 226 is part of the basic structure (L. Chandra Kumar).",
            typicalMisuseOrControversy = "SC frequently nudges petitioners to approach High Courts under 226 first unless pan-India constitutional questions arise."
        ),
        ConstitutionalArticle(
            articleNumber = "Article 356",
            title = "Provisions in Case of Failure of Constitutional Machinery (President's Rule)",
            corePrinciple = "Allows Union executive to assume state government powers on Governor's report or otherwise, subject to strict judicial scrutiny under S.R. Bommai (1994).",
            parliamentaryApplication = "The classic federalism weapon. Quote S.R. Bommai: Presidential proclamation is subject to judicial review; floor test in the Assembly is the ONLY constitutional forum to test majority, not Raj Bhavan lawns.",
            typicalMisuseOrControversy = "Never claim President's Rule can be imposed indefinitely without Parliamentary approval within two months."
        ),
        ConstitutionalArticle(
            articleNumber = "Article 246 & Seventh Schedule",
            title = "Distribution of Legislative Powers (Union, State, Concurrent Lists)",
            corePrinciple = "Delineates legislative competence between Parliament and State Legislatures across Union (List I), State (List II), and Concurrent (List III).",
            parliamentaryApplication = "Essential for federal debates, agriculture, police, public order, and taxation. Cite the 'Doctrine of Pith and Substance' when defending or challenging national laws affecting state subjects.",
            typicalMisuseOrControversy = "Parliament has supremacy only when there is repugnancy in List III under Article 254; it cannot legislate on List II without Articles 249/250/252 procedures."
        ),
        ConstitutionalArticle(
            articleNumber = "Article 280",
            title = "Finance Commission",
            corePrinciple = "Mandates a quasi-judicial body every 5 years to recommend the distribution of net tax proceeds between the Union and the States and grants-in-aid.",
            parliamentaryApplication = "Crucial for southern vs northern states devolution debates, 15th & 16th Finance Commission terms of reference, and cess/surcharge proliferation reducing the divisible pool.",
            typicalMisuseOrControversy = "Cesses and surcharges do not form part of the divisible pool under Article 270; opposition often attacks this as fiscal subversion."
        ),
        ConstitutionalArticle(
            articleNumber = "Article 74 & 75",
            title = "Council of Ministers & Collective Responsibility",
            corePrinciple = "The President acts on the aid and advice of the Council of Ministers headed by the Prime Minister. The Council of Ministers is collectively responsible to the Lok Sabha.",
            parliamentaryApplication = "Direct attack on ministerial accountability. Collective responsibility means the entire Cabinet owns executive blunders. Any minister speaking contrary must resign.",
            typicalMisuseOrControversy = "Individual cabinet members cannot shift blame to civil servants or other ministries under Article 75(3)."
        )
    )

    val indianStatutes = listOf(
        IndianStatute(
            shortName = "BNS 2023",
            officialTitle = "Bharatiya Nyaya Sanhita, 2023",
            year = "2023",
            legalType = "Act (Replaced IPC 1860)",
            coreProvisions = "Replaces IPC; introduces Community Service as punishment; redefines offences against the State (Section 152 replacing Sedition 124A); criminalizes organized crime (Sec 111) and terrorist acts under general law.",
            parliamentaryAngle = "Ruling delegates emphasize decolonization and victim-centric justice; Opposition challenges broad definitions under Section 152 ('acts endangering sovereignty') and overlap with UAPA."
        ),
        IndianStatute(
            shortName = "BNSS 2023",
            officialTitle = "Bharatiya Nagarik Suraksha Sanhita, 2023",
            year = "2023",
            legalType = "Act (Replaced CrPC 1973)",
            coreProvisions = "Governs criminal procedure; electronic FIRs, mandatory videography of search and seizure, forensic investigation for offences punishable with 7+ years, and police custody provisions under Section 187.",
            parliamentaryAngle = "Flashpoint over Section 187(3) which allows police custody in parts during the initial 40 or 60 days, raising debate over prolonged custody and bail safeguards."
        ),
        IndianStatute(
            shortName = "BSA 2023",
            officialTitle = "Bharatiya Sakshya Adhiniyam, 2023",
            year = "2023",
            legalType = "Act (Replaced Evidence Act 1872)",
            coreProvisions = "Modernizes rules of evidence for digital era; electronic and digital records given equivalent legal status as primary evidence, admissibility conditions streamlined.",
            parliamentaryAngle = "Modernization hailed, but questions raised regarding chain of custody and forensic laboratory backlogs across state police forces."
        ),
        IndianStatute(
            shortName = "DPDP Act 2023",
            officialTitle = "Digital Personal Data Protection Act, 2023",
            year = "2023",
            legalType = "Act",
            coreProvisions = "Framework for processing digital personal data; Data Protection Board of India; penalties up to ₹250 crore; broad exemptions for State agencies under Section 17.",
            parliamentaryAngle = "Government highlights citizen privacy rights and digital economy growth; Opposition and civil society attack sweeping government surveillance exemptions under Section 17(2)."
        ),
        IndianStatute(
            shortName = "RPA 1951",
            officialTitle = "Representation of the People Act, 1951",
            year = "1951",
            legalType = "Act",
            coreProvisions = "Conduct of elections, qualifications/disqualifications of MPs/MLAs (Section 8), corrupt practices (Section 123), declaration of assets and criminal antecedents.",
            parliamentaryAngle = "Section 8(3) automatic disqualification on 2-year conviction (Lily Thomas doctrine); debate on paid news, model code of conduct enforcement powers of ECI."
        ),
        IndianStatute(
            shortName = "PMLA 2002",
            officialTitle = "Prevention of Money Laundering Act, 2002",
            year = "2002",
            legalType = "Act",
            coreProvisions = "Confiscation of property derived from scheduled offences; Enforcement Directorate powers; twin bail conditions under Section 45; reverse burden of proof under Section 24.",
            parliamentaryAngle = "Central debate: Opposition alleges weaponization against political rivals; Ruling party quotes Vijay Madanlal Choudhary (2022) where SC upheld stringent PMLA provisions against economic crimes."
        ),
        IndianStatute(
            shortName = "UAPA 1967",
            officialTitle = "Unlawful Activities (Prevention) Act, 1967",
            year = "1967 (amended 2019)",
            legalType = "Act",
            coreProvisions = "Prevention of unlawful activities and terror; 2019 amendment empowers Centre to designate individuals as terrorists; Section 43D(5) stringent bar on bail if prima facie true.",
            parliamentaryAngle = "Watali judgment vs Union of India: Section 43D(5) makes bail virtually impossible during trial. Mention low conviction rates vs high pre-trial detention periods."
        )
    )

    val landmarkJudgments = listOf(
        LandmarkJudgment(
            caseTitle = "Kesavananda Bharati v. State of Kerala",
            year = "1973",
            benchSize = "13 Judges (7:6)",
            coreHolding = "Parliament has wide constituent amending power under Article 368, but CANNOT alter the Basic Structure of the Constitution (democracy, rule of law, federalism, separation of powers, judicial review).",
            constitutionalProvision = "Article 368 & Basic Structure Doctrine",
            relevanceToAippm = "The ultimate shield against any constitutional amendment attempting to subvert democratic checks or judicial independence."
        ),
        LandmarkJudgment(
            caseTitle = "S.R. Bommai v. Union of India",
            year = "1994",
            benchSize = "9 Judges",
            coreHolding = "Article 356 imposition is justiciable and subject to judicial review. Secularism and federalism are basic structure elements. The strength of a Ministry can only be tested on the floor of the House.",
            constitutionalProvision = "Article 356 & Federalism",
            relevanceToAippm = "Quote whenever any delegate discusses dismissal of state governments, Governor's discretionary conduct, or coalition instability."
        ),
        LandmarkJudgment(
            caseTitle = "Justice K.S. Puttaswamy (Retd.) v. Union of India",
            year = "2017",
            benchSize = "9 Judges (Unanimous)",
            coreHolding = "Right to Privacy is a Fundamental Right guaranteed under Article 21 and Part III. Any state infringement must satisfy the three-fold test: Legality, Legitimate State Aim, and Proportionality.",
            constitutionalProvision = "Article 21 & Privacy",
            relevanceToAippm = "Essential for all data protection, state surveillance, Pegasus, wiretapping, and biometric profiling arguments."
        ),
        LandmarkJudgment(
            caseTitle = "Association for Democratic Reforms (ADR) v. Union of India (Electoral Bonds Case)",
            year = "2024",
            benchSize = "5 Judges (Unanimous)",
            coreHolding = "Struck down the 2018 Electoral Bonds Scheme as unconstitutional, violating voters' right to information under Article 19(1)(a). Held unlimited corporate donations to parties violates free and fair elections.",
            constitutionalProvision = "Article 19(1)(a) & Article 14",
            relevanceToAippm = "Huge political ammunition on campaign finance transparency, corporate-political nexus, and quid-pro-quo contracts."
        ),
        LandmarkJudgment(
            caseTitle = "Lily Thomas v. Union of India",
            year = "2013",
            benchSize = "2 Judges",
            coreHolding = "Struck down Section 8(4) of the RPA 1951. Any MP, MLA, or MLC convicted of an offence and sentenced to not less than 2 years stands disqualified immediately without a 3-month protection window.",
            constitutionalProvision = "Article 102(1)(e) & RPA Section 8",
            relevanceToAippm = "Directly relevant to disqualification of political candidates and criminalization in politics."
        ),
        LandmarkJudgment(
            caseTitle = "Government of NCT of Delhi v. Union of India",
            year = "2023",
            benchSize = "5 Judges (CJI Chandrachud bench)",
            coreHolding = "Held that the elected Government of Delhi has legislative and executive control over administrative services in NCT of Delhi (except police, public order, land) under Article 239AA to preserve democratic accountability.",
            constitutionalProvision = "Article 239AA & Federal Governance",
            relevanceToAippm = "Key precedent for Lieutenant Governor vs Chief Minister conflicts, civil service ordinances, and federal autonomy."
        )
    )

    val documentedContradictions = listOf(
        ContradictionItem(
            politicianOrParty = "BJP / Ruling Party",
            issue = "One Nation One Election & Federalism",
            pastPosition = "In earlier committee debates and state manifestos, advocated for strong regional diversity and federal autonomy of state assemblies.",
            pastDate = "2000s Law Commission Consultations",
            pastSource = "Parliamentary Committee Records / Law Commission Working Paper",
            currentPosition = "Pushes for simultaneous elections to Lok Sabha and Assemblies under Kovind Committee recommendations, requiring constitutional amendments shortening state tenures.",
            currentDate = "2023-2024",
            currentSource = "High-Level Committee Report on Simultaneous Elections (PIB)",
            politicalImpact = "Attacked for curtailing the five-year constitutional mandate of state assemblies under Article 172.",
            confrontationQuestion = "If a state government falls in month 14 of a synchronized cycle, does the ruling party intend to suspend democracy under President's rule, or force state voters into an artificial truncated term?"
        ),
        ContradictionItem(
            politicianOrParty = "Indian National Congress (INC)",
            issue = "Article 356 (President's Rule) Record",
            pastPosition = "Invoked Article 356 nearly 90 times during past Union governments to dismiss opposition state governments.",
            pastDate = "1960s–1980s",
            pastSource = "Sarkaria Commission Report & Parliamentary Debates",
            currentPosition = "Portrays itself as the uncompromising vanguard of federalism and constitutional morality against Governors' overreach.",
            currentDate = "2020-2024",
            currentSource = "AICC Official Resolutions & Press Releases",
            politicalImpact = "Vulnerable to historical attacks regarding dismissal of EMS Namboodiripad's Kerala government (1959) and post-1977/1980 mass dissolutions.",
            confrontationQuestion = "Will the Congress party delegate stand before this House and formally apologize on record for the 90 times their party used Raj Bhavan to guillotine democratically elected state assemblies?"
        ),
        ContradictionItem(
            politicianOrParty = "Aam Aadmi Party (AAP)",
            issue = "Alliances & Anti-Corruption Stance",
            pastPosition = "Rose from the India Against Corruption movement promising never to ally with either the Congress or the BJP.",
            pastDate = "2013-2014",
            pastSource = "Press Conferences & Party Founding Declarations",
            currentPosition = "Joined the INDIA alliance and seat-sharing pacts with the Congress in Delhi, Gujarat, and Haryana.",
            currentDate = "2024 General Elections",
            currentSource = "Joint Alliance Press Briefings (PTI)",
            politicalImpact = "Vulnerable to attacks of ideological opportunism and compromising founding anti-corruption principles for survival.",
            confrontationQuestion = "How does the party that began by waving files of corruption against the Congress now share campaign stages and joint press communiqués with the very individuals they swore to investigate?"
        ),
        ContradictionItem(
            politicianOrParty = "Trinamool Congress (AITC)",
            issue = "Central Investigation Agencies & Federal Scrutiny",
            pastPosition = "Demanded CBI and central agency probes into Left Front governance excesses, chit-fund scams, and political violence in West Bengal.",
            pastDate = "2008-2011",
            pastSource = "Lok Sabha Debates & Calcutta High Court Petitions",
            currentPosition = "Withdraws general consent to CBI and accuses ED/CBI of being 'political henchmen' conducting illegal raids against regional state autonomy.",
            currentDate = "2021-2024",
            currentSource = "West Bengal Assembly Resolutions & Press Conferences",
            politicalImpact = "Exposes opportunistic shifting between seeking central judicial intervention when in opposition vs resisting it when in power.",
            confrontationQuestion = "When the CBI arrested your political rivals in 2010, they were defenders of justice; why did they suddenly become tools of tyranny the moment their scrutiny knocked at your ministerial door?"
        )
    )

    val sampleAgendas = listOf(
        "One Nation, One Election: Constitutional Feasibility & Impact on Federal Structure",
        "Implementation of New Criminal Laws (BNS, BNSS, BSA): Civil Liberties vs Modernized Justice",
        "Electoral Funding Reforms, Corporate Transparency & the Post-Electoral Bond Landscape",
        "Delimitation 2026 & Centre-State Fiscal Federalism: Protecting Southern States' Representation",
        "National Security Doctrine, Border Infrastructure & Cross-Border Terror Accountability",
        "Digital Personal Data Protection & State Surveillance: Balancing National Security with Article 21"
    )

    val samplePortfolios = listOf(
        "Narendra Modi (Prime Minister)",
        "Rahul Gandhi (Leader of Opposition)",
        "Amit Shah (Minister of Home Affairs)",
        "Mallikarjun Kharge (Congress President)",
        "Arvind Kejriwal (AAP Convener)",
        "Mamata Banerjee (Chief Minister, West Bengal)",
        "S. Jaishankar (External Affairs Minister)",
        "Nirmala Sitharaman (Finance Minister)",
        "Asaduddin Owaisi (AIMIM President)",
        "Ruling-Party Senior MP",
        "Opposition Frontbencher MP",
        "Independent / Non-Aligned MP"
    )
}
