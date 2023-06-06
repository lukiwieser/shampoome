import { BottleSize } from "./enums";

export class ShampooDetails {
    constructor(
        public nickName: String | null,
        public price: Number | null,
        public bottleSize: BottleSize | null,
        public description: String | null,
        public ingredients: String | null
    ) {}
}