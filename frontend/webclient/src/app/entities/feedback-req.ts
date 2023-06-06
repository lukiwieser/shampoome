export class FeedbackReq {
    constructor(
        public processId: String,
        public overallSatisfaction: String,
        public priceSatisfaction: String,
	    public comments: String,
    ) {}
}