export class ShampooDetails {
    constructor(
        public nickName: String,
        public price: Number,
        public bottleSize: "S" | "M" | "L",
        public description: String,
        public ingredients: String
    ) {}
}