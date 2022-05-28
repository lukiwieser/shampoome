export class Ingredients {
    constructor(
        public nickName: String | null,
        public price: number | null,
	    public bottleSize: String | null,
	    public description: String | null,
	    public ingredients: String | null
    ) {}
}