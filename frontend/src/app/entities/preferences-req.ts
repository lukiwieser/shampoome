export class PreferencesReq {
    constructor(
        public ageOver25: string,
  	    public hairType: string,
        public scalp: string,
        public splitEnds: string,
        public dandruff: string,
        public hairLossMedium: string,
        public hairLossStrong: string,
        public thinHair: string,
        public fragrance: String,
        public bottleSize: String,
        public nickName: String
    ) {}
}