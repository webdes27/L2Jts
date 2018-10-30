package org.jts.dataparser.data.holder;

import org.jts.dataparser.data.annotations.Element;
import org.jts.dataparser.data.holder.setting.common.ClassID;
import org.jts.dataparser.data.holder.skilldata.skillacquire.AcquireInfo;
import org.mmocore.commons.data.AbstractHolder;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author KilRoy
 */
public class SkillAcquireHolder extends AbstractHolder {
    private static final SkillAcquireHolder ourInstance = new SkillAcquireHolder();
    private final Map<Integer, List<AcquireInfo>> skillAcquireClassData = new HashMap<>();
    /*
     * Human fighter block
     */
    @Element(start = "fighter_begin", end = "fighter_end")
    private List<AcquireInfo> fighterAcquire;
    @Element(start = "warrior_begin", end = "warrior_end")
    private List<AcquireInfo> warriorAcquire; //include_fighter
    @Element(start = "warlord_begin", end = "warlord_end")
    private List<AcquireInfo> warlordAcquire; //include_warrior
    @Element(start = "dreadnought_begin", end = "dreadnought_end")
    private List<AcquireInfo> dreadnoughtAcquire; //include_warlord
    @Element(start = "gladiator_begin", end = "gladiator_end")
    private List<AcquireInfo> gladiatorAcquire; //include_warrior
    @Element(start = "duelist_begin", end = "duelist_end")
    private List<AcquireInfo> duelistAcquire; //include_gladiator
    @Element(start = "knight_begin", end = "knight_end")
    private List<AcquireInfo> knightAcquire; //include_fighter
    @Element(start = "paladin_begin", end = "paladin_end")
    private List<AcquireInfo> paladinAcquire; //include_knight
    @Element(start = "phoenix_knight_begin", end = "phoenix_knight_end")
    private List<AcquireInfo> phoenixKnightAcquire; //include_paladin
    @Element(start = "dark_avenger_begin", end = "dark_avenger_end")
    private List<AcquireInfo> darkAvengerAcquire; //include_knight
    @Element(start = "hell_knight_begin", end = "hell_knight_end")
    private List<AcquireInfo> hellKnightAcquire; //include_dark_avenger
    @Element(start = "rogue_begin", end = "rogue_end")
    private List<AcquireInfo> rogueAcquire; //include_fighter
    @Element(start = "treasure_hunter_begin", end = "treasure_hunter_end")
    private List<AcquireInfo> treasureHunterAcquire; //include_rogue
    @Element(start = "adventurer_begin", end = "adventurer_end")
    private List<AcquireInfo> adventurerAcquire; //include_treasure_hunter
    @Element(start = "hawkeye_begin", end = "hawkeye_end")
    private List<AcquireInfo> hawkeyeAcquire; //include_rogue
    @Element(start = "sagittarius_begin", end = "sagittarius_end")
    private List<AcquireInfo> sagittariusAcquire; //include_hawkeye
    /*
     * Elven fighter block
     */
    @Element(start = "elven_fighter_begin", end = "elven_fighter_end")
    private List<AcquireInfo> elvenFighterAcquire;
    @Element(start = "elven_knight_begin", end = "elven_knight_end")
    private List<AcquireInfo> elvenKnightAcquire; //include_elven_fighter
    @Element(start = "temple_knight_begin", end = "temple_knight_end")
    private List<AcquireInfo> templeKnightAcquire; //include_elven_knight
    @Element(start = "evas_templar_begin", end = "evas_templar_end")
    private List<AcquireInfo> evasTemplarAcquire; //include_temple_knight
    @Element(start = "swordsinger_begin", end = "swordsinger_end")
    private List<AcquireInfo> swordsingerAcquire; //include_elven_knight
    @Element(start = "sword_muse_begin", end = "sword_muse_end")
    private List<AcquireInfo> swordMuseAcquire; //include_swordsinger
    @Element(start = "elven_scout_begin", end = "elven_scout_end")
    private List<AcquireInfo> elvenScoutAcquire; //include_elven_fighter
    @Element(start = "plain_walker_begin", end = "plain_walker_end")
    private List<AcquireInfo> plainWalkerAcquire; //include_elven_scout
    @Element(start = "wind_rider_begin", end = "wind_rider_end")
    private List<AcquireInfo> windRiderAcquire; //include_plain_walker
    @Element(start = "silver_ranger_begin", end = "silver_ranger_end")
    private List<AcquireInfo> silverRangerAcquire; //include_elven_scout
    @Element(start = "moonlight_sentinel_begin", end = "moonlight_sentinel_end")
    private List<AcquireInfo> moonlightSentinelAcquire; //include_silver_ranger
    /*
     * Dark Elven fighter block
     */
    @Element(start = "dark_fighter_begin", end = "dark_fighter_end")
    private List<AcquireInfo> darkFighterAcquire;
    @Element(start = "palus_knight_begin", end = "palus_knight_end")
    private List<AcquireInfo> palusKnightAcquire; //include_dark_fighter
    @Element(start = "shillien_knight_begin", end = "shillien_knight_end")
    private List<AcquireInfo> shillienKnightAcquire; //include_palus_knight
    @Element(start = "shillien_templar_begin", end = "shillien_templar_end")
    private List<AcquireInfo> shillienTemplarAcquire; //include_shillien_knight
    @Element(start = "bladedancer_begin", end = "bladedancer_end")
    private List<AcquireInfo> bladedancerAcquire; //include_palus_knight
    @Element(start = "spectral_dancer_begin", end = "spectral_dancer_end")
    private List<AcquireInfo> spectralDancerAcquire; //include_bladedancer
    @Element(start = "assasin_begin", end = "assasin_end")
    private List<AcquireInfo> assasinAcquire; //include_dark_fighter
    @Element(start = "abyss_walker_begin", end = "abyss_walker_end")
    private List<AcquireInfo> abyssWalkerAcquire; //include_assasin
    @Element(start = "ghost_hunter_begin", end = "ghost_hunter_end")
    private List<AcquireInfo> ghostHunterAcquire; //include_abyss_walker
    @Element(start = "phantom_ranger_begin", end = "phantom_ranger_end")
    private List<AcquireInfo> phantomRangerAcquire; //include_assasin
    @Element(start = "ghost_sentinel_begin", end = "ghost_sentinel_end")
    private List<AcquireInfo> ghostSentinelAcquire; //include_phantom_ranger
    /*
     * Orc fighter block
     */
    @Element(start = "orc_fighter_begin", end = "orc_fighter_end")
    private List<AcquireInfo> orcFighterAcquire;
    @Element(start = "orc_raider_begin", end = "orc_raider_end")
    private List<AcquireInfo> orcRaiderAcquire; //include_orc_fighter
    @Element(start = "destroyer_begin", end = "destroyer_end")
    private List<AcquireInfo> destroyerAcquire; //include_orc_raider
    @Element(start = "titan_begin", end = "titan_end")
    private List<AcquireInfo> titanAcquire; //include_destroyer
    @Element(start = "orc_monk_begin", end = "orc_monk_end")
    private List<AcquireInfo> orcMonkAcquire; //include_orc_fighter
    @Element(start = "tyrant_begin", end = "tyrant_end")
    private List<AcquireInfo> tyrantAcquire; //include_orc_monk
    @Element(start = "grand_khavatari_begin", end = "grand_khavatari_end")
    private List<AcquireInfo> grandKhavatariAcquire; //include_tyrant
    /*
     * Dwarf fighter block
     */
    @Element(start = "dwarven_fighter_begin", end = "dwarven_fighter_end")
    private List<AcquireInfo> dwarvenFighterAcquire;
    @Element(start = "scavenger_begin", end = "scavenger_end")
    private List<AcquireInfo> scavengerAcquire; //include_dwarven_fighter
    @Element(start = "bounty_hunter_begin", end = "bounty_hunter_end")
    private List<AcquireInfo> bountyHunterAcquire; //include_scavenger
    @Element(start = "fortune_seeker_begin", end = "fortune_seeker_end")
    private List<AcquireInfo> fortuneSeekerAcquire; //include_bounty_hunter
    @Element(start = "artisan_begin", end = "artisan_end")
    private List<AcquireInfo> artisanAcquire; //include_dwarven_fighter
    @Element(start = "warsmith_begin", end = "warsmith_end")
    private List<AcquireInfo> warsmithAcquire; //include_artisan
    @Element(start = "maestro_begin", end = "maestro_end")
    private List<AcquireInfo> maestroAcquire; //include_warsmith
    /*
     * Kamael block
     */
    //Male
    @Element(start = "kamael_m_soldier_begin", end = "kamael_m_soldier_end")
    private List<AcquireInfo> kamaelMSoldierAcquire;
    @Element(start = "trooper_begin", end = "trooper_end")
    private List<AcquireInfo> trooperAcquire;    //include_kamael_m_soldier
    @Element(start = "berserker_begin", end = "berserker_end")
    private List<AcquireInfo> berserkerAcquire;    //include_trooper
    @Element(start = "doombringer_begin", end = "doombringer_end")
    private List<AcquireInfo> doombringerAcquire;    //include_berserker
    @Element(start = "m_soul_breaker_begin", end = "m_soul_breaker_end")
    private List<AcquireInfo> mSoulBreakerAcquire;    //include_trooper
    @Element(start = "m_soul_hound_begin", end = "m_soul_hound_end")
    private List<AcquireInfo> mSoulHoundAcquire;    //include_m_soul_breaker
    //Female
    @Element(start = "kamael_f_soldier_begin", end = "kamael_f_soldier_end")
    private List<AcquireInfo> kamaelFSoldierAcquire;
    @Element(start = "warder_begin", end = "warder_end")
    private List<AcquireInfo> warderAcquire;    //include_kamael_f_soldier
    @Element(start = "arbalester_begin", end = "arbalester_end")
    private List<AcquireInfo> arbalesterAcquire;    //include_warder
    @Element(start = "trickster_begin", end = "trickster_end")
    private List<AcquireInfo> tricksterAcquire;    //include_arbalester
    @Element(start = "f_soul_breaker_begin", end = "f_soul_breaker_end")
    private List<AcquireInfo> fSoulBreakerAcquire;    //include_warder
    @Element(start = "f_soul_hound_begin", end = "f_soul_hound_end")
    private List<AcquireInfo> fSoulHoundAcquire;    //include_f_soul_breaker
    @Element(start = "inspector_begin", end = "inspector_end")
    private List<AcquireInfo> inspectorAcquire;    //include_warder
    @Element(start = "judicator_begin", end = "judicator_end")
    private List<AcquireInfo> judicatorAcquire;    //include_inspector
    /*
     * Human mage block
     */
    @Element(start = "mage_begin", end = "mage_end")
    private List<AcquireInfo> mageAcquire;
    @Element(start = "wizard_begin", end = "wizard_end")
    private List<AcquireInfo> wizardAcquire; //include_mage
    @Element(start = "sorcerer_begin", end = "sorcerer_end")
    private List<AcquireInfo> sorcererAcquire; //include_wizard
    @Element(start = "archmage_begin", end = "archmage_end")
    private List<AcquireInfo> archmageAcquire; //include_sorcerer
    @Element(start = "necromancer_begin", end = "necromancer_end")
    private List<AcquireInfo> necromancerAcquire; //include_wizard
    @Element(start = "soultaker_begin", end = "soultaker_end")
    private List<AcquireInfo> soultakerAcquire; //include_necromancer
    @Element(start = "warlock_begin", end = "warlock_end")
    private List<AcquireInfo> warlockAcquire; //include_wizard
    @Element(start = "arcana_lord_begin", end = "arcana_lord_end")
    private List<AcquireInfo> arcanaLordAcquire; //include_warlock
    @Element(start = "cleric_begin", end = "cleric_end")
    private List<AcquireInfo> clericAcquire; //include_mage
    @Element(start = "bishop_begin", end = "bishop_end")
    private List<AcquireInfo> bishopAcquire; //include_cleric
    @Element(start = "cardinal_begin", end = "cardinal_end")
    private List<AcquireInfo> cardinalAcquire; //include_bishop
    @Element(start = "prophet_begin", end = "prophet_end")
    private List<AcquireInfo> prophetAcquire; //include_cleric
    @Element(start = "hierophant_begin", end = "hierophant_end")
    private List<AcquireInfo> hierophantAcquire; //include_prophet
    /*
     * Elven mage block
     */
    @Element(start = "elven_mage_begin", end = "elven_mage_end")
    private List<AcquireInfo> elvenMageAcquire;
    @Element(start = "elven_wizard_begin", end = "elven_wizard_end")
    private List<AcquireInfo> elvenWizardAcquire; //include_elven_mage
    @Element(start = "spellsinger_begin", end = "spellsinger_end")
    private List<AcquireInfo> spellsingerAcquire; //include_elven_wizard
    @Element(start = "mystic_muse_begin", end = "mystic_muse_end")
    private List<AcquireInfo> mysticMuseAcquire; //include_spellsinger
    @Element(start = "elemental_summoner_begin", end = "elemental_summoner_end")
    private List<AcquireInfo> elementalSummonerAcquire; //include_elven_wizard
    @Element(start = "elemental_master_begin", end = "elemental_master_end")
    private List<AcquireInfo> elementalMasterAcquire; //include_elemental_summoner
    @Element(start = "oracle_begin", end = "oracle_end")
    private List<AcquireInfo> oracle_Acquire; //include_elven_mage
    @Element(start = "elder_begin", end = "elder_end")
    private List<AcquireInfo> elderAcquire; //include_oracle
    @Element(start = "evas_saint_begin", end = "evas_saint_end")
    private List<AcquireInfo> evasSaintAcquire; //include_elder
    /*
     * Dark mage block
     */
    @Element(start = "dark_mage_begin", end = "dark_mage_end")
    private List<AcquireInfo> darkMageAcquire;
    @Element(start = "dark_wizard_begin", end = "dark_wizard_end")
    private List<AcquireInfo> darkWizardAcquire; //include_dark_mage
    @Element(start = "spellhowler_begin", end = "spellhowler_end")
    private List<AcquireInfo> spellhowlerAcquire; //include_dark_wizard
    @Element(start = "storm_screamer_begin", end = "storm_screamer_end")
    private List<AcquireInfo> stormScreamerAcquire; //include_spellhowler
    @Element(start = "phantom_summoner_begin", end = "phantom_summoner_end")
    private List<AcquireInfo> phantomSummonerAcquire; //include_dark_wizard
    @Element(start = "spectral_master_begin", end = "spectral_master_end")
    private List<AcquireInfo> spectralMasterAcquire; //include_phantom_summoner
    @Element(start = "shillien_oracle_begin", end = "shillien_oracle_end")
    private List<AcquireInfo> shillienOracleAcquire; //include_dark_mage
    @Element(start = "shillien_elder_begin", end = "shillien_elder_end")
    private List<AcquireInfo> shillienElderAcquire; //include_shillien_oracle
    @Element(start = "shillien_saint_begin", end = "shillien_saint_end")
    private List<AcquireInfo> shillienSaintAcquire; //include_shillien_elder
    /*
     * Orc mage block
     */
    @Element(start = "orc_mage_begin", end = "orc_mage_end")
    private List<AcquireInfo> orcMageAcquire;
    @Element(start = "orc_shaman_begin", end = "orc_shaman_end")
    private List<AcquireInfo> orcShamanAcquire; //include_orc_mage
    @Element(start = "overlord_begin", end = "overlord_end")
    private List<AcquireInfo> overlordAcquire; //include_orc_shaman
    @Element(start = "dominator_begin", end = "dominator_end")
    private List<AcquireInfo> dominatorAcquire; //include_overlord
    @Element(start = "warcryer_begin", end = "warcryer_end")
    private List<AcquireInfo> warcryerAcquire; //include_orc_shaman
    @Element(start = "doomcryer_begin", end = "doomcryer_end")
    private List<AcquireInfo> doomcryerAcquire; //include_warcryer
    /*
     * Other skill trees block
     */
    @Element(start = "fishing_begin", end = "fishing_end")
    private List<AcquireInfo> fishingAcquire;
    @Element(start = "pledge_begin", end = "pledge_end")
    private List<AcquireInfo> pledgeAcquire;
    @Element(start = "sub_pledge_begin", end = "sub_pledge_end")
    private List<AcquireInfo> subPledgeAcquire;
    @Element(start = "transform_begin", end = "transform_end")
    private List<AcquireInfo> transformAcquire;
    @Element(start = "subjob_begin", end = "subjob_end")
    private List<AcquireInfo> subjobAcquire;
    @Element(start = "collect_begin", end = "collect_end")
    private List<AcquireInfo> collectAcquire;
    @Element(start = "bishop_sharing_begin", end = "bishop_sharing_end")
    private List<AcquireInfo> bishopSharingAcquire;
    @Element(start = "elder_sharing_begin", end = "elder_sharing_end")
    private List<AcquireInfo> elderSharingAcquire;
    @Element(start = "silen_elder_sharing_begin", end = "silen_elder_sharing_end")
    private List<AcquireInfo> silenElderSharingAcquire;

    private SkillAcquireHolder() {
    }

    public static SkillAcquireHolder getInstance() {
        return ourInstance;
    }

    @Override
    public void afterParsing() {
        super.afterParsing();
        skillAcquireClassData.put(ClassID.fighter.getClassId(), fighterAcquire);
        skillAcquireClassData.put(ClassID.warrior.getClassId(), this.warriorAcquire);
        skillAcquireClassData.put(ClassID.warlord.getClassId(), this.warlordAcquire);
        skillAcquireClassData.put(ClassID.dreadnought.getClassId(), this.dreadnoughtAcquire);
        skillAcquireClassData.put(ClassID.gladiator.getClassId(), this.gladiatorAcquire);
        skillAcquireClassData.put(ClassID.duelist.getClassId(), this.duelistAcquire);
        skillAcquireClassData.put(ClassID.knight.getClassId(), this.knightAcquire);
        skillAcquireClassData.put(ClassID.paladin.getClassId(), this.paladinAcquire);
        skillAcquireClassData.put(ClassID.phoenix_knight.getClassId(), this.phoenixKnightAcquire);
        skillAcquireClassData.put(ClassID.dark_avenger.getClassId(), this.darkAvengerAcquire);
        skillAcquireClassData.put(ClassID.hell_knight.getClassId(), this.hellKnightAcquire);
        skillAcquireClassData.put(ClassID.rogue.getClassId(), this.rogueAcquire);
        skillAcquireClassData.put(ClassID.treasure_hunter.getClassId(), this.treasureHunterAcquire);
        skillAcquireClassData.put(ClassID.adventurer.getClassId(), this.adventurerAcquire);
        skillAcquireClassData.put(ClassID.hawkeye.getClassId(), this.hawkeyeAcquire);
        skillAcquireClassData.put(ClassID.sagittarius.getClassId(), this.sagittariusAcquire);
        skillAcquireClassData.put(ClassID.mage.getClassId(), this.mageAcquire);
        skillAcquireClassData.put(ClassID.wizard.getClassId(), this.wizardAcquire);
        skillAcquireClassData.put(ClassID.sorceror.getClassId(), this.sorcererAcquire);
        skillAcquireClassData.put(ClassID.archmage.getClassId(), this.archmageAcquire);
        skillAcquireClassData.put(ClassID.necromancer.getClassId(), this.necromancerAcquire);
        skillAcquireClassData.put(ClassID.soultaker.getClassId(), this.soultakerAcquire);
        skillAcquireClassData.put(ClassID.warlock.getClassId(), this.warlockAcquire);
        skillAcquireClassData.put(ClassID.arcana_lord.getClassId(), this.arcanaLordAcquire);
        skillAcquireClassData.put(ClassID.cleric.getClassId(), this.clericAcquire);
        skillAcquireClassData.put(ClassID.bishop.getClassId(), this.bishopAcquire);
        skillAcquireClassData.put(ClassID.cardinal.getClassId(), this.cardinalAcquire);
        skillAcquireClassData.put(ClassID.prophet.getClassId(), this.prophetAcquire);
        skillAcquireClassData.put(ClassID.hierophant.getClassId(), this.hierophantAcquire);

        skillAcquireClassData.put(ClassID.elven_fighter.getClassId(), this.elvenFighterAcquire);
        skillAcquireClassData.put(ClassID.elven_knight.getClassId(), this.elvenKnightAcquire);
        skillAcquireClassData.put(ClassID.temple_knight.getClassId(), this.templeKnightAcquire);
        skillAcquireClassData.put(ClassID.eva_templar.getClassId(), this.evasTemplarAcquire);
        skillAcquireClassData.put(ClassID.sword_singer.getClassId(), this.swordsingerAcquire);
        skillAcquireClassData.put(ClassID.sword_muse.getClassId(), this.swordMuseAcquire);
        skillAcquireClassData.put(ClassID.elven_scout.getClassId(), this.elvenScoutAcquire);
        skillAcquireClassData.put(ClassID.plains_walker.getClassId(), this.plainWalkerAcquire);
        skillAcquireClassData.put(ClassID.wind_rider.getClassId(), this.windRiderAcquire);
        skillAcquireClassData.put(ClassID.silver_ranger.getClassId(), this.silverRangerAcquire);
        skillAcquireClassData.put(ClassID.moonlight_sentinel.getClassId(), this.moonlightSentinelAcquire);
        skillAcquireClassData.put(ClassID.elven_mage.getClassId(), this.elvenMageAcquire);
        skillAcquireClassData.put(ClassID.elven_wizard.getClassId(), this.elvenWizardAcquire);
        skillAcquireClassData.put(ClassID.spellsinger.getClassId(), this.spellsingerAcquire);
        skillAcquireClassData.put(ClassID.mystic_muse.getClassId(), this.mysticMuseAcquire);
        skillAcquireClassData.put(ClassID.elemental_summoner.getClassId(), this.elementalSummonerAcquire);
        skillAcquireClassData.put(ClassID.elemental_master.getClassId(), this.elementalMasterAcquire);
        skillAcquireClassData.put(ClassID.oracle.getClassId(), this.oracle_Acquire);
        skillAcquireClassData.put(ClassID.elder.getClassId(), this.elderAcquire);
        skillAcquireClassData.put(ClassID.eva_saint.getClassId(), this.evasSaintAcquire);

        skillAcquireClassData.put(ClassID.dark_fighter.getClassId(), this.darkFighterAcquire);
        skillAcquireClassData.put(ClassID.palus_knight.getClassId(), this.palusKnightAcquire);
        skillAcquireClassData.put(ClassID.shillien_knight.getClassId(), this.shillienKnightAcquire);
        skillAcquireClassData.put(ClassID.shillien_templar.getClassId(), this.shillienTemplarAcquire);
        skillAcquireClassData.put(ClassID.bladedancer.getClassId(), this.bladedancerAcquire);
        skillAcquireClassData.put(ClassID.spectral_dancer.getClassId(), this.spectralDancerAcquire);
        skillAcquireClassData.put(ClassID.assassin.getClassId(), this.assasinAcquire);
        skillAcquireClassData.put(ClassID.abyss_walker.getClassId(), this.abyssWalkerAcquire);
        skillAcquireClassData.put(ClassID.ghost_hunter.getClassId(), this.ghostHunterAcquire);
        skillAcquireClassData.put(ClassID.phantom_ranger.getClassId(), this.phantomRangerAcquire);
        skillAcquireClassData.put(ClassID.ghost_sentinel.getClassId(), this.ghostSentinelAcquire);
        skillAcquireClassData.put(ClassID.dark_mage.getClassId(), this.darkMageAcquire);
        skillAcquireClassData.put(ClassID.dark_wizard.getClassId(), this.darkWizardAcquire);
        skillAcquireClassData.put(ClassID.spellhowler.getClassId(), this.spellhowlerAcquire);
        skillAcquireClassData.put(ClassID.storm_screamer.getClassId(), this.stormScreamerAcquire);
        skillAcquireClassData.put(ClassID.phantom_summoner.getClassId(), this.phantomSummonerAcquire);
        skillAcquireClassData.put(ClassID.spectral_master.getClassId(), this.spectralMasterAcquire);
        skillAcquireClassData.put(ClassID.shillien_oracle.getClassId(), this.shillienOracleAcquire);
        skillAcquireClassData.put(ClassID.shillien_elder.getClassId(), this.shillienElderAcquire);
        skillAcquireClassData.put(ClassID.shillien_saint.getClassId(), this.shillienSaintAcquire);

        skillAcquireClassData.put(ClassID.orc_fighter.getClassId(), this.orcFighterAcquire);
        skillAcquireClassData.put(ClassID.orc_raider.getClassId(), this.orcRaiderAcquire);
        skillAcquireClassData.put(ClassID.destroyer.getClassId(), this.destroyerAcquire);
        skillAcquireClassData.put(ClassID.titan.getClassId(), this.titanAcquire);
        skillAcquireClassData.put(ClassID.orc_monk.getClassId(), this.orcMonkAcquire);
        skillAcquireClassData.put(ClassID.tyrant.getClassId(), this.tyrantAcquire);
        skillAcquireClassData.put(ClassID.grand_khauatari.getClassId(), this.grandKhavatariAcquire);
        skillAcquireClassData.put(ClassID.orc_mage.getClassId(), this.orcMageAcquire);
        skillAcquireClassData.put(ClassID.orc_shaman.getClassId(), this.orcShamanAcquire);
        skillAcquireClassData.put(ClassID.overlord.getClassId(), this.overlordAcquire);
        skillAcquireClassData.put(ClassID.dominator.getClassId(), this.dominatorAcquire);
        skillAcquireClassData.put(ClassID.warcryer.getClassId(), this.warcryerAcquire);
        skillAcquireClassData.put(ClassID.doomcryer.getClassId(), this.doomcryerAcquire);

        skillAcquireClassData.put(ClassID.dwarven_fighter.getClassId(), this.dwarvenFighterAcquire);
        skillAcquireClassData.put(ClassID.scavenger.getClassId(), this.scavengerAcquire);
        skillAcquireClassData.put(ClassID.bounty_hunter.getClassId(), this.bountyHunterAcquire);
        skillAcquireClassData.put(ClassID.fortune_seeker.getClassId(), this.fortuneSeekerAcquire);
        skillAcquireClassData.put(ClassID.artisan.getClassId(), this.artisanAcquire);
        skillAcquireClassData.put(ClassID.warsmith.getClassId(), this.warsmithAcquire);
        skillAcquireClassData.put(ClassID.maestro.getClassId(), this.maestroAcquire);

        skillAcquireClassData.put(ClassID.kamael_m_soldier.getClassId(), this.kamaelMSoldierAcquire);
        skillAcquireClassData.put(ClassID.trooper.getClassId(), this.trooperAcquire);
        skillAcquireClassData.put(ClassID.berserker.getClassId(), this.berserkerAcquire);
        skillAcquireClassData.put(ClassID.doombringer.getClassId(), this.doombringerAcquire);
        skillAcquireClassData.put(ClassID.m_soul_breaker.getClassId(), this.mSoulBreakerAcquire);
        skillAcquireClassData.put(ClassID.m_soul_hound.getClassId(), this.mSoulHoundAcquire);
        skillAcquireClassData.put(ClassID.kamael_f_soldier.getClassId(), this.kamaelFSoldierAcquire);
        skillAcquireClassData.put(ClassID.warder.getClassId(), this.warderAcquire);
        skillAcquireClassData.put(ClassID.arbalester.getClassId(), this.arbalesterAcquire);
        skillAcquireClassData.put(ClassID.trickster.getClassId(), this.tricksterAcquire);
        skillAcquireClassData.put(ClassID.f_soul_breaker.getClassId(), this.fSoulBreakerAcquire);
        skillAcquireClassData.put(ClassID.f_soul_hound.getClassId(), this.fSoulHoundAcquire);
        skillAcquireClassData.put(ClassID.inspector.getClassId(), this.inspectorAcquire);
        skillAcquireClassData.put(ClassID.judicator.getClassId(), this.judicatorAcquire);
    }

    public Map<Integer, List<AcquireInfo>> getSkillAcquireClassData() {
        return skillAcquireClassData;
    }

    public List<AcquireInfo> getAcquireInfo(final int classId) {
        return skillAcquireClassData.get(classId);
    }

    @Override
    public void clear() {
        skillAcquireClassData.clear();
    }

    @Override
    public int size() {
        return skillAcquireClassData.size();
    }
}