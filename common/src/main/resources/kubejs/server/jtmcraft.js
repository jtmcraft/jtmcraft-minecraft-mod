const minecraftColors = [
    'white',
    'orange',
    'magenta',
    'light_blue',
    'yellow',
    'lime',
    'pink',
    'gray',
    'light_gray',
    'cyan',
    'purple',
    'blue',
    'brown',
    'green',
    'red',
    'black'
];

function getRandomElement(array) {
    const randomIndex = Math.floor(Math.random() * array.length);
    return array[randomIndex];
}

function selectWeighted(items) {
    const totalWeight = items.reduce((accumulator, item) => accumulator + (item.weight || 0), 0);
    let remaining = Math.random() * totalWeight;

    for (const item of items) {
        remaining -= item.weight || 0;
        if (remaining < 0) {
            return {
                item: item.item,
                count: item.count
            };
        }
    }
    return null;
}

function givePlayerItems(player, items) {
    items.forEach(item => player.give(item));
}

function giveFirstSpawnItems(event, options) {
    event.player.give({ item: `minecraft:${getRandomElement(options.colors)}_bed`, count: 1 });

    givePlayerItems(event.player, options.bonusItems);

    if (options.randomWaystone) {
        const waystone = selectWeighted(options.waystones);
        if (waystone !== null) {
            event.player.give(waystone);
        }
    } else {
        event.player.give({ item: "waystones:waystone", count: 1 });
    }
}

function onPlayerLoggedIn(event, options) {
    if (!event.hasGameStage(options.spawnBonusStage)) {
        event.addGameStage(options.spawnBonusStage);
        giveFirstSpawnItems(event, options);
    }
}

const options = {
    spawnBonusStage: 'jtmcraft_bonus',
    colors: minecraftColors,
    randomWaystone: false,
    waystones: [
        { item: "waystones:waystone", weight: 3, count: 1 },
        { item: "waystones:mossy_waystone", weight: 1, count: 1 },
        { item: "waystones:sandy_waystone", weight: 1, count: 1 }
    ],
    bonusItems: [
        { item: "jtmcraft:wooden_mining_tool", count: 1 },
        { item: "minecraft:bread", count: 5 },
        { item: "minecraft:torch", count: 4 },
        { item: "minecraft:acacia_log", count: 8 }
    ],
};

PlayerEvents.loggedIn((event) => onPlayerLoggedIn(event, options));
