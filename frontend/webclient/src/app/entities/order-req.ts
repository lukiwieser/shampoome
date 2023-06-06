export class OrderReq {
    constructor(
        public processId: String,
        public shippingAddress: String,
	    public matriculationNumber: String,
    ) {}
}