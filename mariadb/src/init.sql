CREATE TABLE Orders (
    Id UUID not NULL default UUID() PRIMARY KEY,
    MatriculationNumber VARCHAR(8),
    Nickname VARCHAR(100),
    Address VARCHAR(100),
    Ingredients TEXT,
    Price DECIMAL,
    processId TEXT,
    Status ENUM('order_placed','on_the_way','delivered'),
    bottleSize TEXT, # ENUM('S','M','L'),
    isDelayed BOOL,
    description TEXT
)